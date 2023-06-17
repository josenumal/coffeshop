package com.inatlas.coffeeshop.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode(callSuper = true)
public class PaidReceiptItem extends FreeReceiptItem {

    private final BigDecimal unitPrice;
    private final BigDecimal total;

    public PaidReceiptItem(final int amount, final String productName, final BigDecimal unitPrice, final BigDecimal total) {
        super(amount, productName);
        this.unitPrice = unitPrice;
        this.total = total;
    }

}
