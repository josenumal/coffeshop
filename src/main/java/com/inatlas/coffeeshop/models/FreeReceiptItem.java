package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FreeReceiptItem {

    private int amount;
    private String productName;

}
