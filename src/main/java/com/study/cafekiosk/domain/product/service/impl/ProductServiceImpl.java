package com.study.cafekiosk.domain.product.service.impl;

import com.study.cafekiosk.domain.product.dto.ProductCreateRequestDto;
import com.study.cafekiosk.domain.product.entity.ProductEntity;
import com.study.cafekiosk.domain.product.entity.ProductType;
import com.study.cafekiosk.domain.product.repository.ProductRepository;
import com.study.cafekiosk.domain.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity create(ProductCreateRequestDto productCreateRequestDto){

        validateStock(productCreateRequestDto);

        String productNumber = generateProductNumber();

        Integer stock = productCreateRequestDto.getProductType() == ProductType.BOTTLE ? null : productCreateRequestDto.getStock();

        ProductEntity productEntity = new ProductEntity(productNumber, productCreateRequestDto.getProductType(), stock);

        return productRepository.save(productEntity);
    }

    @Override
    public ProductEntity update(Long id, ProductCreateRequestDto productCreateRequestDto){

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("상품없음"));

        productEntity.update(
           productCreateRequestDto.getProductType(),
                productCreateRequestDto.getStock()
        );
        return productEntity;
    }


    private void validateStock(ProductCreateRequestDto productCreateRequestDto){
        if(productCreateRequestDto.getProductType() != ProductType.BOTTLE && (productCreateRequestDto.getStock() == null || productCreateRequestDto.getStock() < 0)){
            throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        }
    }

    private String generateProductNumber() {
        long count = productRepository.count() + 1;
        return String.format("%03d", count);
    }

}
