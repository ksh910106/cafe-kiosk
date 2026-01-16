package com.study.cafekiosk.domain.product.service;

import com.study.cafekiosk.domain.product.dto.ProductCreateRequestDto;
import com.study.cafekiosk.domain.product.entity.ProductEntity;

import java.util.List;

public interface ProductService {

    ProductEntity create(ProductCreateRequestDto productCreateRequestDto);
    ProductEntity update(Long id,ProductCreateRequestDto productCreateRequestDto);
    List<ProductEntity> findAvailableProducts();
}
