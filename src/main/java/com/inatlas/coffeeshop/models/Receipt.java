package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Receipt {

    private Set<ReceiptItem> receiptItemSet;
    private Set<FreeReceiptItem> freeReceiptItemSet;
    private float total;
    private int discountPercent;

}
