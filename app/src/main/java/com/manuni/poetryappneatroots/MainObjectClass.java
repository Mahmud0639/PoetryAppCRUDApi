package com.manuni.poetryappneatroots;

import com.manuni.poetryappneatroots.models.PoetryModel;

import java.util.List;

public class MainObjectClass {
    private String status;
    private String message;
    private List<PoetryModel> data;

    public MainObjectClass(String status, String message, List<PoetryModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PoetryModel> getData() {
        return data;
    }

    public void setData(List<PoetryModel> data) {
        this.data = data;
    }
}
