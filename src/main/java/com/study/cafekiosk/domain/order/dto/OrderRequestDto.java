package com.study.cafekiosk.domain.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {

    private List<OrderProductRequestDto> products;

}
