package com.samyyc.setu.vo;

import lombok.Data;

import java.util.List;

@Data
public class SetuResponse {

    private String error;
    private List<SetuData> data;

    public SetuData getData() {
        if (!"".equals(error)) {
            return null;
        } else if (data.size() == 0) {
            return null;
        } else if (data.get(0).getUrl() == null) {
            return null;
        } else {
            return data.get(0);
        }
    }

    public SetuResponse() {

    }

    public SetuResponse(String error, List<SetuData> data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(List<SetuData> data) {
        this.data = data;
    }

    public List<SetuData> getDatas() {
        return data;
    }
}
