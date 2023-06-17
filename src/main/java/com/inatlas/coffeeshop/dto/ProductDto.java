package com.inatlas.coffeeshop.dto;

import com.inatlas.coffeeshop.models.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private int id;

    private String productName;

    private ProductType productType;

    private float price;

    private boolean available;

}
