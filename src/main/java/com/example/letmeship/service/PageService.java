package com.example.letmeship.service;

import com.example.letmeship.entity.History;
import com.example.letmeship.entity.Page;
import com.example.letmeship.exception.InvalidUrlException;
import com.example.letmeship.exception.PageCannotBeReachedException;
import com.example.letmeship.exception.TimeoutException;
import com.example.letmeship.repository.HistoryRepository;
import com.example.letmeship.repository.PageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private HistoryRepository historyRepository;

    private static final Logger logger = LoggerFactory.getLogger(PageService.class);

    public Page savePageWithLinks(String url) throws IOException {
        validateUrl(url);
        Page page = findPageByUrl(url);
        if (page == null) {
            page = new Page();
            page.setUrl(url);
            page.setLastRequestedTime(LocalDateTime.now());

            List<String> links = extractLinksFromUrl(page, url);
            page.setLinks(links);
            savePage(page);
        } else {
            page.setLastRequestedTime(LocalDateTime.now());
            savePage(page);
        }

        saveHistory(url);

        return page;
    }

    public Page savePage(Page page) {
        return pageRepository.save(page);
    }

    public Page findPageByUrl(String url) {
        return pageRepository.findByUrl(url);
    }

    private List<String> extractLinksFromUrl(Page page, String url) {
        logger.info("entered: " + url);
        List<String> linkList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("a[href]");
            for (Element linkElement : links) {
                logger.debug("links: " + linkElement.attr("abs:href"));
                linkList.add(linkElement.attr("abs:href"));
            }
            String title = document.title();
            page.setTitle(title);
        }catch (SocketTimeoutException e) {
            throw new TimeoutException("Request to URL timed out: " + url);
        }catch (IOException e) {
            throw new PageCannotBeReachedException("Page cannot be reached, URL: " + url);
        }
        return linkList;
    }

    private void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException("Invalid URL: " + url);
        }
    }

    private void saveHistory(String url) {
        History history = new History();
        history.setRequestedUrl(url);
        history.setRequestedTime(LocalDateTime.now());
        historyRepository.save(history);
    }

    public org.springframework.data.domain.Page<History> getHistory(Pageable pageable) {
        return historyRepository.findAll(pageable);
    }
}
