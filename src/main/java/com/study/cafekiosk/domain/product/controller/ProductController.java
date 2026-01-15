package com.study.cafekiosk.domain.product.controller;

import com.study.cafekiosk.domain.product.dto.ProductCreateRequestDto;
import com.study.cafekiosk.domain.product.dto.ProductResponseDto;
import com.study.cafekiosk.domain.product.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return new ProductResponseDto(productService.create(productCreateRequestDto));
    }


}
