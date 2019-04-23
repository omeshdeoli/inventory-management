package com.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

}
