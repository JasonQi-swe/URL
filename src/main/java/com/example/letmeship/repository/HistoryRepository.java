package com.example.letmeship.repository;

import com.example.letmeship.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Page<History> findAll(Pageable pageable);
}
