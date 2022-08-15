package com.samyyc.setu.cache;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.samyyc.setu.Setu;
import com.samyyc.setu.config.CacheConfig;
import com.samyyc.setu.config.GlobalConfig;
import com.samyyc.setu.util.*;
import com.samyyc.setu.vo.SetuData;
import com.samyyc.setu.vo.SetuLocalCache;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetuCache {
    private static File normalSetuDir = new File(Setu.INSTANCE.getDataFolder()+"/cache/normal");
    private static File r18SetuDir = new File(Setu.INSTANCE.getDataFolder()+"/cache/r18");
    private static File configFile = new File(Setu.INSTANCE.getConfigFolder(), "config.yml");
    private static CacheConfig config;
    private static File normalDataFile;
    private static File r18DataFile;
    private static JSONArray normalJsonData;
    private static JSONArray r18JsonData;

    private static Boolean normalCacheGetting = false;
    private static Boolean r18CacheGetting = false;
    private static ExecutorService normalExecutorService = Executors.newFixedThreadPool(1);
    private static ExecutorService r18ExecutorService = Executors.newFixedThreadPool(1);
    public static void init(File file) {
        FileUtil.checkFolderExist(normalSetuDir);
        FileUtil.checkFolderExist(r18SetuDir);

        Properties properties = ConfigUtil.getProperties(file);
        config = ConfigUtil.properties2CacheConfig(properties);

        normalDataFile = new File(Setu.INSTANCE.getDataFolder(), "normalData.json");
        r18DataFile = new File(Setu.INSTANCE.getDataFolder(), "r18Data.json");

        FileUtil.checkExist(normalDataFile);
        FileUtil.checkExist(r18DataFile);



        String normalDataString = FileUtil.readFile(normalDataFile);
        String r18DataString = FileUtil.readFile(r18DataFile);
        normalJsonData = JSONObject.parseArray(normalDataString);
        r18JsonData = JSONObject.parseArray(r18DataString);

        if (normalJsonData == null) normalJsonData = new JSONArray();
        if (r18JsonData == null) r18JsonData = new JSONArray();

    }

    public static boolean isEnable() {
        return config.isEnable();
    }

    public static Message getSetuCacheMessage(boolean r18, Contact contact) {
        if (r18) {
            if (!GlobalConfig.r18EnableMasterControl) {
                return GlobalConfig.r18BannedMessage;
            }
        }
        int maxCache = r18 ? config.getMaxR18SetuCache() : config.getMaxNormalSetuCache();
        JSONArray jsonData = r18 ? r18JsonData : normalJsonData;
        File dataFile = r18 ? r18DataFile : normalDataFile;
        return getSetuCacheMessage(contact, jsonData, dataFile, maxCache, r18);

    }

    public static Message getSetuCacheMessage(Contact contact, JSONArray jsonData, File dataFile, int maxCache, boolean r18) {
        if (jsonData == null || jsonData.size() == 0) {
            if (maxCache % 20 != 0) {
                throw new RuntimeException("缓存最大数必须是20的倍数");
            } else {
                int times = maxCache / 20;
                cacheNewSetu(r18, times);
                return MessageUtil.getSetuMessageFromApi(r18, contact);
            }
        }
        JSONObject jsonObject = jsonData.getJSONObject(0);
        SetuLocalCache localCache = jsonObject.toJavaObject(SetuLocalCache.class);
        jsonData.remove(0);
        FileUtil.saveJSONArrayToFile(jsonData, dataFile);

        boolean getting = r18 ? r18CacheGetting : normalCacheGetting;
        if (maxCache - jsonData.size() >= 20 && !getting) {
            int times = (maxCache - jsonData.size()) / 20;
            cacheNewSetu(r18, times);
        }

        Image image = ExternalResource.uploadAsImage(localCache.getFile(), contact);
        System.out.println("成功从缓存读取图片: "+localCache.getData().getPid()+" R18: "+r18);
        localCache.getFile().delete();

        return MessageUtil.setuData2QQMessage(image, localCache.getData(), contact);
    }

    public static void cacheNewSetu(boolean r18, int times) {
        ExecutorService executorService = r18 ? r18ExecutorService : normalExecutorService;
        executorService.submit(() -> {
            if (r18) {
                r18CacheGetting = true;
            } else {
                normalCacheGetting = true;
            }
            for (int i = 0 ; i < times; i++) {
                List<SetuData> setuDataList = SetuUtil.getSetuList(r18, 20);
                while (setuDataList == null) {
                    setuDataList = SetuUtil.getSetuList(r18, 20);
                    Setu.INSTANCE.getLogger().error("获取缓存失败，5秒后重试");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int j = 0;
                for (SetuData data : setuDataList) {
                    j++;
                    File dir = r18 ? r18SetuDir : normalSetuDir;
                    File file = getImageCacheFile(data, dir, r18);
                    SetuLocalCache localCache = new SetuLocalCache(file, data);
                    if (!r18) {
                        normalJsonData.add(localCache);
                        FileUtil.saveJSONArrayToFile(normalJsonData, normalDataFile);
                    } else {
                        r18JsonData.add(localCache);
                        FileUtil.saveJSONArrayToFile(r18JsonData, r18DataFile);
                    }
                    if (r18) {
                        Setu.INSTANCE.getLogger().info("添加R18缓存 ("+(i+1)+"/"+times+") ("+j+"/20)");
                    } else {
                        Setu.INSTANCE.getLogger().info("添加普通缓存 ("+(i+1)+"/"+times+") ("+j+"/20)");
                    }


                }
                /*
                if (!r18) {

                } else {
                }

                 */
            }
            if (r18) {
                r18CacheGetting = false;
                System.out.println("R18缓存补充成功");
            } else {
                normalCacheGetting = false;
                System.out.println("普通缓存补充成功");
            }
            return null;
        });
    }

    public static File getImageCacheFile(SetuData data, File dir, boolean r18) {
        File file = new File(dir, data.getPid()+"."+data.getExt());
        try {
            file.createNewFile();
            URL url = new URL(data.getUrl());

            if (HttpUtil.checkImageExist(url.toString())) {
                data = SetuUtil.getSetu(r18);
                return SetuUtil.getImageFile(data);
            }
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel fileChannel = fileOutputStream.getChannel();

            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            fileOutputStream.close();
            //FileUtils.copyURLToFile(new URL(data.getUrl()), file, 10000, 10000);

            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
