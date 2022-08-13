package com.samyyc.setu.cache;

import com.samyyc.setu.Setu;
import com.samyyc.setu.config.CacheConfig;
import com.samyyc.setu.util.ConfigUtil;
import com.samyyc.setu.util.SetuUtil;
import com.samyyc.setu.vo.SetuData;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SetuCache {

    private static File normalSetuDir = new File(Setu.INSTANCE.getDataFolder(), "normal/");
    private static File r18SetuDir = new File(Setu.INSTANCE.getDataFolder(), "r18/");
    private static File configFile = new File(Setu.INSTANCE.getConfigFolder(), "config.yml");
    private static CacheConfig config;
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);
    public static void init(File file) {
        Properties properties = ConfigUtil.getProperties(file);
        config = ConfigUtil.properties2CacheConfig(properties);
    }

    public static Image getNormalSetuCache() {

    }

    public static void cacheNewSetu(boolean r18) {
        executorService.submit(() -> {
            List<SetuData> setuDataList = SetuUtil.getSetuList(r18, 20);
            for (SetuData data : setuDataList) {
                getNormalImageCacheFile(data);
            }
        });
    }

    public static File getNormalImageCacheFile(SetuData data) {
        File file = new File(normalSetuDir, data.getPid()+data.getExt());
        try {
            file.createNewFile();
            FileUtils.copyURLToFile(new URL(data.getUrl()), file);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
