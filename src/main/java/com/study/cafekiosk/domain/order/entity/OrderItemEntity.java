package com.study.cafekiosk.domain.order.entity;

import com.study.cafekiosk.domain.product.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Fetch;

@Entity
@Getter
@Table(name = "ORDER_ITEMS_TBL")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    private int quantity;

    protected OrderItemEntity(){

    }

    public OrderItemEntity(OrderEntity order, ProductEntity product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }
}
