package com.study.cafekiosk.domain.product.dto;

import com.study.cafekiosk.domain.product.entity.ProductType;
import lombok.Getter;

@Getter
public class ProductCreateRequestDto {

    private ProductType productType;
    private Integer stock;
}
