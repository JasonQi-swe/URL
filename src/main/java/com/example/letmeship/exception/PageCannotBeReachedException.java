package com.example.letmeship.exception;

public class PageCannotBeReachedException extends RuntimeException {
    public PageCannotBeReachedException(String message) {
        super(message);
    }
}
