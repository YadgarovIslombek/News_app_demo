package com.example.news_app_demo.Api.model;

import java.util.List;

public class MainDataClass {
    private List<ObjectDataClass> articles;

    public MainDataClass() {
    }

    public MainDataClass(List<ObjectDataClass> articles) {
        this.articles = articles;
    }

    public List<ObjectDataClass> getArticles() {
        return articles;
    }

    public void setArticles(List<ObjectDataClass> articles) {
        this.articles = articles;
    }
}
