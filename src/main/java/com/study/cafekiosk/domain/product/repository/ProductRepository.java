package com.study.cafekiosk.domain.product.repository;

import com.study.cafekiosk.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    long count();
}
