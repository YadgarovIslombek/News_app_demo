package com.example.news_app_demo.model;

public class SourceModel {
    private String id;
    private String name;

    public SourceModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SourceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
