package com.samyyc.setu.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HttpUtil {

    public static StringEntity serializeHttpEntity(boolean r18) {
        JSONObject json = new JSONObject();
        json.put("size", "regular");
        json.put("r18", r18);
        return new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
    }

    public static StringEntity serializeHttpEntity(boolean r18, int count) {
        JSONObject json = new JSONObject();
        json.put("size", "regular");
        json.put("r18", r18);
        json.put("num", count);
        return new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
    }

    public static StringEntity serializeHttpEntity(boolean r18, String... tags) {
        JSONObject json = new JSONObject();
        JSONArray tagsJsonArray = new JSONArray();
        tagsJsonArray.addAll(Arrays.stream(tags).collect(Collectors.toList()));
        json.put("size", "regular");
        json.put("tag", tagsJsonArray);
        json.put("r18", r18);
        return new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);
    }

    public static boolean checkImageExist(String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();

        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            response = client.execute(get);
            return response.getStatusLine().getStatusCode() == 200;

        } catch (IOException e) {
            return true;
        }
    }

}
