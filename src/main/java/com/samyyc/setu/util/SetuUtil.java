package com.samyyc.setu.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.samyyc.setu.Setu;
import com.samyyc.setu.vo.SetuData;
import com.samyyc.setu.vo.SetuResponse;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetuUtil {

    public static String apiUrl = "http://api.lolicon.app/setu/v2";

    public static Image getImage(SetuData data, Contact contact) {
        return ExternalResource.uploadAsImage(getImageFile(data), contact);
    }

    public static File getImageFile(SetuData data) {
        File file = new File("../cache/temp." + data.getExt());
        return HttpUtil.getImageFile(file, data);
    }

    public static SetuData getSetu(boolean r18) {
        HttpPost post = new HttpPost(apiUrl);
        post.setEntity(HttpUtil.serializeHttpEntity(r18));
       return getSetuData(post);
    }

    public static List<SetuData> getSetuList(boolean r18, int count) {
        HttpPost post = new HttpPost(apiUrl);
        post.setEntity(HttpUtil.serializeHttpEntity(r18, count));
        return getSetuDataList(post);
    }

    public static SetuData getSetu(boolean r18, String... tags) {
        HttpPost post = new HttpPost(apiUrl);
        //post.setHeader("Content-Type","application/json;charset=utf-8");
        post.setEntity(HttpUtil.serializeHttpEntity(r18, tags));
        return getSetuData(post);
    }

    public static SetuData getSetuData(HttpPost post) {
        CloseableHttpClient client = HttpClientBuilder.create().build();

            CloseableHttpResponse response;
            try {
                // 图床可能出现图片不存在，返回错误数据
                while (true) {
                    response = client.execute(post);
                    SetuResponse setuResponse = getSetuResponse(response);
                    if (setuResponse.getData() != null) {
                        if (HttpUtil.checkImageExist(setuResponse.getData().getUrl())) {
                            client.close();
                            return setuResponse.getData();
                        } else {

                        }
                    } else {
                        client.close();
                        return null;

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();

                Setu.INSTANCE.getLogger().error("[MiraiSetuPlugin] 调用API时出现错误!");
                return null;
            }
    }

    public static List<SetuData> getSetuDataList(HttpPost post) {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            client.close();
            SetuResponse setuResponse = getSetuResponse(response);
            return setuResponse.getDatas();

        } catch (IOException e) {
            Setu.INSTANCE.getLogger().error("[MiraiSetuPlugin] 调用API时出现错误!");
            return null;
        }
    }

    public static SetuResponse getSetuResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity resEntity = response.getEntity();
        String rawResponse = EntityUtils.toString(resEntity);
        JSONObject jsonResponse = JSONObject.parseObject(rawResponse);
        response.close();

        return jsonResponse.toJavaObject(SetuResponse.class);
    }



}
