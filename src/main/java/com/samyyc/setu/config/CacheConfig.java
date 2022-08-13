package com.samyyc.setu.config;

public class CacheConfig {

    private boolean enable;
    private int maxNormalSetuCache;
    private int maxR18SetuCache;

    public CacheConfig() {

    }

    public CacheConfig(boolean enable, int maxNormalSetuCache, int maxR18SetuCache) {
        this.enable = enable;
        this.maxNormalSetuCache = maxNormalSetuCache;
        this.maxR18SetuCache = maxR18SetuCache;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getMaxNormalSetuCache() {
        return maxNormalSetuCache;
    }

    public void setMaxNormalSetuCache(int maxNormalSetuCache) {
        this.maxNormalSetuCache = maxNormalSetuCache;
    }

    public int getMaxR18SetuCache() {
        return maxR18SetuCache;
    }

    public void setMaxR18SetuCache(int maxR18SetuCache) {
        this.maxR18SetuCache = maxR18SetuCache;
    }
}
