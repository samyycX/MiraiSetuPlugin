package com.samyyc.setu.vo;

import java.io.File;

public class SetuLocalCache {

    private File file;
    private SetuData data;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public SetuData getData() {
        return data;
    }

    public void setData(SetuData data) {
        this.data = data;
    }

    public SetuLocalCache(File file, SetuData data) {
        this.file= file;
        this.data = data;
    }

    public SetuLocalCache() {
    }
}
