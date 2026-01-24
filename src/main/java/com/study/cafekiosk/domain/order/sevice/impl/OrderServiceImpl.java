package com.study.cafekiosk.domain.order.sevice.impl;

import com.study.cafekiosk.domain.order.dto.OrderProductRequestDto;
import com.study.cafekiosk.domain.order.dto.OrderRequestDto;
import com.study.cafekiosk.domain.order.entity.OrderEntity;
import com.study.cafekiosk.domain.order.entity.OrderItemEntity;
import com.study.cafekiosk.domain.order.repository.OrderRepository;
import com.study.cafekiosk.domain.order.sevice.OrderService;
import com.study.cafekiosk.domain.product.entity.ProductEntity;
import com.study.cafekiosk.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderEntity createOrder(OrderRequestDto orderRequestDto) {

        validateOrderTime();

        OrderEntity order = new OrderEntity(LocalDateTime.now());

        for (OrderProductRequestDto item : orderRequestDto.getProducts()) {

            ProductEntity product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

            // 재고 체크 (병 제외)
            if (product.getStock() != null) {
                if (product.getStock() < item.getQuantity()) {
                    throw new IllegalStateException("재고 부족");
                }
                product.decreaseStock(item.getQuantity());
            }

            OrderItemEntity orderItem =
                    new OrderItemEntity(order, product, item.getQuantity());

            order.addOrderItem(orderItem);
        }

        return orderRepository.save(order);

    }

    private void validateOrderTime() {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(10,0);
        LocalTime end = LocalTime.of(2,0);

        boolean isOrderable =!(now.isBefore(start) && now.isAfter(end));

        if(!isOrderable) {
            throw   new IllegalStateException("주문가능한 시간이 아닙니다.");
        }

    }
}
