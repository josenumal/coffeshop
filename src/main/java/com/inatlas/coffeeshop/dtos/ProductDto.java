package com.inatlas.coffeeshop.dtos;

import com.inatlas.coffeeshop.models.ProductType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private int id;

    private String productName;

    private ProductType productType;

    private BigDecimal price;

    private boolean available;

}
