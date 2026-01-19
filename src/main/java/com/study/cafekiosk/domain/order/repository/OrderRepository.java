package com.study.cafekiosk.domain.order.repository;

import com.study.cafekiosk.domain.order.entity.OrderEntity;
import com.study.cafekiosk.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    boolean existsByOrderItems_Product_IdAndOrderStatus(Long productId, OrderStatus status);

}
