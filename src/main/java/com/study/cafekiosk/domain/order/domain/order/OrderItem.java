package com.study.cafekiosk.domain.order.domain.order;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private OrderEntity orderEntity;

    private int orderPrice;
    private int quantity;



}
