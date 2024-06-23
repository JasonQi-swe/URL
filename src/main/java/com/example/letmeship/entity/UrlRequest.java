package com.example.letmeship.entity;


import jakarta.validation.constraints.NotNull;

public class UrlRequest {

    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=url;
    }
}