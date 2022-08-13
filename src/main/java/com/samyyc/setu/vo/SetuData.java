package com.samyyc.setu.vo;

import java.util.List;

public class SetuData {

    private Integer pid;
    private Integer p;
    private Integer uid;
    private Integer width;
    private Integer height;

    private String title;
    private String author;
    private String ext;

    private Boolean r18;

    private Long uploadDate;

    private List<String> tags;

    private SetuUrl urls;

    public String getUrl() {
        return urls.getUrl();
    }

    public SetuData() {
    }

    public SetuData(Integer pid, Integer p, Integer uid, Integer width, Integer height, String title, String author, String ext, Boolean r18, Long uploadDate, List<String> tags, SetuUrl urls) {
        this.pid = pid;
        this.p = p;
        this.uid = uid;
        this.width = width;
        this.height = height;
        this.title = title;
        this.author = author;
        this.ext = ext;
        this.r18 = r18;
        this.uploadDate = uploadDate;
        this.tags = tags;
        this.urls = urls;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Boolean getR18() {
        return r18;
    }

    public void setR18(Boolean r18) {
        this.r18 = r18;
    }

    public Long getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Long uploadDate) {
        this.uploadDate = uploadDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public SetuUrl getUrls() {
        return urls;
    }

    public void setUrls(SetuUrl urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "SetuData{" +
                "pid=" + pid +
                ", p=" + p +
                ", uid=" + uid +
                ", width=" + width +
                ", height=" + height +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ext='" + ext + '\'' +
                ", r18=" + r18 +
                ", uploadDate=" + uploadDate +
                ", tags=" + tags +
                ", urls=" + urls +
                '}';
    }
}
