package com.example.letmeship.entity;

import lombok.Data;

@Data
public class DefaultErrorResponse {
    private String message;
    private String details;

    public DefaultErrorResponse(String message, String exception) {
        this.message = message;
        this.details = details;
    }
}