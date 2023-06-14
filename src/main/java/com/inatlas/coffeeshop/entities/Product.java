package com.inatlas.coffeeshop.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT", schema = "COFFEE_SHOP_DEV")
@Data
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private int productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "AVAILABLE")
    private boolean available;

}
