package com.inatlas.coffeeshop.entities;

import com.inatlas.coffeeshop.models.ProductType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT", schema = "COFFEE_SHOP_DEV")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRODUCT_TYPE")
    private ProductType productType;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "AVAILABLE")
    private boolean available;

}

