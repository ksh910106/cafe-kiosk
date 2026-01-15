package com.study.cafekiosk.domain.product.dto;


import com.study.cafekiosk.domain.product.entity.ProductEntity;
import lombok.Getter;

@Getter
public class ProductResponseDto {

    private String productNumber;
    private String productType;
    private Integer stock;

    public ProductResponseDto(ProductEntity product) {
        this.productNumber = product.getProductNumber();
        this.productType = product.getProductType().name();
        this.stock = product.getStock();
    }

}
