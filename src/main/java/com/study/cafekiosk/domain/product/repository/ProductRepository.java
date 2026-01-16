package com.study.cafekiosk.domain.product.repository;

import com.study.cafekiosk.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    long count();// ??

    List<ProductEntity> findByStockIsNullOrStockGreaterThan(Integer stock);
}
