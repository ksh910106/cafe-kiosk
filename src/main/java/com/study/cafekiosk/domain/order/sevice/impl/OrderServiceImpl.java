package com.study.cafekiosk.domain.order.sevice.impl;

import com.study.cafekiosk.domain.order.dto.OrderProductRequestDto;
import com.study.cafekiosk.domain.order.dto.OrderRequestDto;
import com.study.cafekiosk.domain.order.entity.OrderEntity;
import com.study.cafekiosk.domain.order.entity.OrderItemEntity;
import com.study.cafekiosk.domain.order.entity.OrderStatus;
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

    @Override
    @Transactional
    public void completeOrder(Long orderId){

        OrderEntity order = getOrder(orderId);

        if(order.getOrderStatus() != OrderStatus.PREPARING){
            throw new IllegalStateException("준비중인 주문만 완료할 수 있습니다.");
        }
        order.complete();
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId){

        OrderEntity order = getOrder(orderId);

        if(order.getOrderStatus() != OrderStatus.PREPARING){
            throw new IllegalStateException("준비중인 주문만 취소할 수 있습니다.");
        }

        for(OrderItemEntity item : order.getOrderItems()) {
            item.getProduct().increaseStock(item.getQuantity());
        }

        order.cancel();
    }

    private OrderEntity getOrder(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));
    }


    private void validateOrderTime() {

        LocalTime now = LocalTime.now();

        if(now.isBefore(LocalTime.of(10,0)) || now.isAfter(LocalTime.of(22,0))) {
            throw new IllegalStateException("주문 가능한 시간이 아닙니다.");
        }
    }
}
