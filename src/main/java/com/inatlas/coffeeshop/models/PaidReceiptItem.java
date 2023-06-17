package com.inatlas.coffeeshop.models;

import lombok.Getter;

@Getter
public class PaidReceiptItem extends FreeReceiptItem implements ReceiptItem {

    private final float unitPrice;
    private final float total;

    public PaidReceiptItem(final int amount, final String productName, final float unitPrice, final float total) {
        super(amount, productName);
        this.unitPrice = unitPrice;
        this.total = total;
    }

}
