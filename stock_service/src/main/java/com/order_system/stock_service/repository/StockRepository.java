package com.order_system.stock_service.repository;

import com.order_system.stock_service.data.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {
}
