package com.study.cafekiosk.domain.order.sevice;

import com.study.cafekiosk.domain.order.dto.OrderRequestDto;
import com.study.cafekiosk.domain.order.entity.OrderEntity;

public interface OrderService {

    OrderEntity createOrder(OrderRequestDto orderRequestDto);
}
