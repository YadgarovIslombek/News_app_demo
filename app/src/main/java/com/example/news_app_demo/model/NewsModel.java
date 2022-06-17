package com.example.news_app_demo.model;

import java.util.List;

public class NewsModel {
    private String status;
    private String totalResult;
    private List<ObjectDataClass> articles;

    public NewsModel(String status, String totalResult, List<ObjectDataClass> articles) {
        this.status = status;
        this.totalResult = totalResult;
        this.articles = articles;
    }

    public NewsModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public List<ObjectDataClass> getArticles() {
        return articles;
    }

    public void setArticles(List<ObjectDataClass> articles) {
        this.articles = articles;
    }
}
