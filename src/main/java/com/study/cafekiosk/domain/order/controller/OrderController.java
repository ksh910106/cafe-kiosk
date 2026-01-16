package com.study.cafekiosk.domain.order.controller;

import com.study.cafekiosk.domain.order.dto.OrderRequestDto;
import com.study.cafekiosk.domain.order.entity.OrderEntity;
import com.study.cafekiosk.domain.order.sevice.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String create(@RequestBody OrderRequestDto request) {

        OrderEntity order = orderService.createOrder(request);

        return "주문 생성 완료. 주문 ID = " + order.getId();
    }

}
