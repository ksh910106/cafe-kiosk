package com.study.cafekiosk.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="ORDERS_TBL")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    protected OrderEntity() {

    }

    public OrderEntity(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        this.orderStatus = OrderStatus.PREPARING;
    }

    public void addOrderItem(OrderItemEntity orderItemEntity) {
        this.orderItems.add(orderItemEntity);
    }

}
