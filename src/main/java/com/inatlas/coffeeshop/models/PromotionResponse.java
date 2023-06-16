package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PromotionResponse {

    private boolean isApplicable;
    private Receipt receipt;
    private String description;

}
