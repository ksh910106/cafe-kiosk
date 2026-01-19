package com.study.cafekiosk.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name="PRODUCT_TBL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {

    @Id //테이블의 pk설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //PK값을 DB가 자동으로 생성하도록 설정
    //IDENTITY: auto increment
    private Long id;

    @Column(nullable = false, unique = true, length=3)
    //컬럼 제약설정
    //nullable = false : null값 허용X
    //unique = true : 중복값 허용X
    private String productNumber;

    @Enumerated(EnumType.STRING)
    //enum타입 DB에 저장방식
    //STRING : enum이름 그대로 저장
    @Column(nullable = false)
    private ProductType productType;

    private Integer stock;

    public ProductEntity(String productNumber, ProductType productType, Integer stock) {
        this.productNumber = productNumber;
        this.productType = productType;
        this.stock = stock;
    }

    public void update(ProductType type, Integer stock){
         this.productType = type;
         this.stock = stock;
    }

    public void decreaseStock(Integer quantity){

        if(this.stock == null){
            return;
        }

        if(this.stock<quantity){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= quantity;
    }

    public void increaseStock(Integer quantity){
        if(this.stock == null){
            return;
        }
        this.stock += quantity;
    }
}
