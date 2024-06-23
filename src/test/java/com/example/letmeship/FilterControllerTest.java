package com.example.letmeship;

import com.example.letmeship.config.FilterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilterConfig filterConfig;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testEnableRateLimiting() throws Exception {
        mockMvc.perform(post("/internal/filter/rate-limiting/enable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(filterConfig.isRateLimitingEnabled());
    }

    @Test
    public void testDisableRateLimiting() throws Exception {
        mockMvc.perform(post("/internal/filter/rate-limiting/disable")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertFalse(filterConfig.isRateLimitingEnabled());
    }

    @Test
    public void testGetRateLimitingStatus() throws Exception {
        filterConfig.setRateLimitingEnabled(true);

        mockMvc.perform(get("/internal/filter/rate-limiting/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));


        filterConfig.setRateLimitingEnabled(false);

        mockMvc.perform(get("/internal/filter/rate-limiting/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
