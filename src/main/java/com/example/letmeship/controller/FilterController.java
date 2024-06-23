package com.example.letmeship.controller;

import com.example.letmeship.config.FilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/filter")
@CrossOrigin(origins = "*")
public class FilterController {

    @Autowired
    private FilterConfig filterConfig;
    private static final Logger logger = LoggerFactory.getLogger(FilterController.class);

    @PostMapping("/rate-limiting/enable")
    public void enableRateLimiting() {
        filterConfig.setRateLimitingEnabled(true);
        logger.info("The rate limit filter is enabled");
    }

    @PostMapping("/rate-limiting/disable")
    public void disableRateLimiting() {
        filterConfig.setRateLimitingEnabled(false);
        logger.info("The rate limit filter is disabled");
    }

    @GetMapping("/rate-limiting/status")
    public boolean getRateLimitingStatus() {
        return filterConfig.isRateLimitingEnabled();
    }
}
