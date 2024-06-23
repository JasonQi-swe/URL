package com.example.letmeship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {

    @Value("${rate.limiting.enabled:false}")
    private boolean rateLimitingEnabled;

    public boolean isRateLimitingEnabled() {
        return rateLimitingEnabled;
    }

    public void setRateLimitingEnabled(boolean rateLimitingEnabled) {
        this.rateLimitingEnabled = rateLimitingEnabled;
    }
}
