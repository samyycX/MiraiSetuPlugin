package com.samyyc.setu.util;

import com.samyyc.setu.config.CacheConfig;

import java.io.*;
import java.util.Properties;

public class ConfigUtil {

    public static void initConfig(File file) {
        Properties properties = getProperties(file);

        properties.setProperty("cache.enable", "false");
        properties.setProperty("cache.maxNormalSetuCache", "100");
        properties.setProperty("cache.maxR18SetuCache","100");

        saveProperties(properties, file);

    }

    public static Properties getProperties(File file) {
        try {
            Properties properties = new Properties();
            String path = file.getAbsolutePath();
            FileReader fileReader = new FileReader(path);
            properties.load(fileReader);
            fileReader.close();
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveProperties(Properties properties, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
            properties.store(fileWriter, "init");
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CacheConfig properties2CacheConfig(Properties properties) {
        boolean enable = Boolean.parseBoolean(properties.getProperty("cache.enable", "false"));
        int maxNormalSetuCache = Integer.parseInt(properties.getProperty("cache.maxNormalSetuCache","0"));
        int maxR18SetuCache = Integer.parseInt(properties.getProperty("cache.maxR18SetuCache","0"));
        return new CacheConfig(enable, maxNormalSetuCache, maxR18SetuCache);
    }

}
