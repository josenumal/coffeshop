package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Receipt {

    private Set<ReceiptItem> receiptItemSet;
    private float total;

}
