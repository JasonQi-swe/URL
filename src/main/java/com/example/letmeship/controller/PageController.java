package com.example.letmeship.controller;

import com.example.letmeship.entity.History;
import com.example.letmeship.entity.Page;
import com.example.letmeship.entity.UrlRequest;
import com.example.letmeship.service.PageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/page")
@CrossOrigin(origins = "*")
public class PageController {

    @Autowired
    private PageService pageService;

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @PostMapping("/analyse")
    public ResponseEntity<Page> getLinksFromUrl(@Valid @RequestBody UrlRequest urlRequest) throws IOException {
        logger.info("Entered: " + urlRequest.getUrl());
        Page page = pageService.savePageWithLinks(urlRequest.getUrl());
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<org.springframework.data.domain.Page<History>> getHistory(@RequestParam int page, @RequestParam int size) {
        logger.info("Fetching history");
        Pageable pageable = PageRequest.of(page, size);
        org.springframework.data.domain.Page<History> historyPage = pageService.getHistory(pageable);
        return new ResponseEntity<>(historyPage, HttpStatus.OK);
    }
}
