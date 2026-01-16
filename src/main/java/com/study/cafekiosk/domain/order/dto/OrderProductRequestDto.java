package com.study.cafekiosk.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderProductRequestDto {

    private Long productId;
    private int quantity;
}
