package com.study.cafekiosk.domain.product.controller;

import com.study.cafekiosk.domain.product.dto.ProductCreateRequestDto;
import com.study.cafekiosk.domain.product.dto.ProductResponseDto;
import com.study.cafekiosk.domain.product.entity.ProductEntity;
import com.study.cafekiosk.domain.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductCreateRequestDto productCreateRequestDto) {

        ProductEntity productEntity = productService.update(id,productCreateRequestDto);

        return new ProductResponseDto(productEntity);
    }

}
