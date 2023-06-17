package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class FreeReceiptItem {

    private int amount;
    private String productName;

}
