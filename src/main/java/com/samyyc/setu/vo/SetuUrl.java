package com.samyyc.setu.vo;

import lombok.Data;

@Data
public class SetuUrl {

    private String original;
    private String regular;
    private String small;
    private String thumb;
    private String mini;



    public SetuUrl() {

    }

    public SetuUrl(String original, String regular, String small, String thumb, String mini) {
        this.original = original;
        this.regular = regular;
        this.small = small;
        this.thumb = thumb;
        this.mini = mini;
    }

    public String getUrl() {
        if (!original.equals("")) return original;
        if (!regular.equals("")) return regular;
        if (!small.equals("")) return small;
        if (!thumb.equals("")) return thumb;
        if (!mini.equals("")) return mini;
        return null;
    }

    public String toString() {
        return regular;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMini() {
        return mini;
    }

    public void setMini(String mini) {
        this.mini = mini;
    }
}
