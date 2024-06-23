package com.example.letmeship;

import com.example.letmeship.entity.History;
import com.example.letmeship.entity.Page;
import com.example.letmeship.entity.UrlRequest;
import com.example.letmeship.service.PageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PageService pageService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Order(1)
    @Test
    public void testExtractLinksFromUrl() throws Exception {
        UrlRequest urlRequest = new UrlRequest();
        urlRequest.setUrl("https://www.google.com/");

        mockMvc.perform(post("/page/analyse")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"https://www.google.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertTrue(response.contains("https://policies.google.com/terms"));
                });
    }

    @Order(2)
    @Test
    public void testGetHistory() throws Exception {

        mockMvc.perform(get("/page/history")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {

                    String response = result.getResponse().getContentAsString();
                    assertTrue(response.contains("requestedUrl"));
                    assertTrue(response.contains("requestedTime"));
                });
    }
}
