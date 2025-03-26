package com.example.demo.schedular.repository;

import com.example.demo.schedular.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory,Integer> {
    List<PriceHistory> findByProductId (String productId);
}