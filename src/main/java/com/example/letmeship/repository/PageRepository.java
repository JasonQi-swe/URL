package com.example.letmeship.repository;

import com.example.letmeship.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PageRepository extends JpaRepository<Page, Long> {
    Page findByUrl(String url);
}
