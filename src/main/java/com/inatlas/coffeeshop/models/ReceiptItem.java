package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptItem {

    private int amount;
    private String productName;
    private float unitPrice;
    private float total;

}
