package com.example.letmeship.filter;

import com.example.letmeship.config.FilterConfig;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter implements Filter {

    @Value("${max.requests.per.minute:100}")
    private int MAX_REQUESTS_PER_MINUTE;
    private final int TIME_WINDOW_MINUTES = 1;
    private static final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    @Autowired
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!filterConfig.isRateLimitingEnabled()) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String clientIp = httpRequest.getRemoteAddr();
        String requestUri = httpRequest.getRequestURI();
        String key = clientIp + ":" + requestUri;

        requestCounts.putIfAbsent(key, new AtomicInteger(0));
        AtomicInteger count = requestCounts.get(key);

        if (count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Too many requests - rate limit exceeded\"}");
            httpResponse.getWriter().flush();
            return;
        }

        chain.doFilter(request, response);
    }

    @Scheduled(fixedRate = TIME_WINDOW_MINUTES * 60000)
    public void resetCounts() {
        requestCounts.clear();
    }

}
