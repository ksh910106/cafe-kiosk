package com.study.cafekiosk.domain.product.service.impl;

import com.study.cafekiosk.domain.order.entity.OrderStatus;
import com.study.cafekiosk.domain.order.repository.OrderRepository;
import com.study.cafekiosk.domain.product.dto.ProductCreateRequestDto;
import com.study.cafekiosk.domain.product.entity.ProductEntity;
import com.study.cafekiosk.domain.product.entity.ProductType;
import com.study.cafekiosk.domain.product.repository.ProductRepository;
import com.study.cafekiosk.domain.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
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

        boolean preparingOrder = orderRepository.existsByOrderItems_Product_IdAndOrderStatus(id, OrderStatus.PREPARING);

        if(preparingOrder){
            throw new IllegalStateException("주문중인 상품은 수정할 수 없습니다.");
        }

        productEntity.update(
           productCreateRequestDto.getProductType(),
                productCreateRequestDto.getStock()
        );
        return productEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductEntity> findAvailableProducts() {
        return productRepository.findByStockIsNullOrStockGreaterThan(0);
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
