package com.inatlas.coffeeshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private int productId;

    private String productName;

    private ProductType productType;

    private float price;

    private boolean available;

}
