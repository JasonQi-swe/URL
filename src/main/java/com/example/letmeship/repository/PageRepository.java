package com.example.letmeship.repository;

import com.example.letmeship.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository extends JpaRepository<Page, Long> {
    Page findByUrl(String url);


   // @Query("SELECT p FROM Page p LEFT JOIN FETCH p.links WHERE p.url = :url")
   // Page findByUrlWithLinks(String url);
}
