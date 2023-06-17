package com.inatlas.coffeeshop.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class Receipt {

    private Set<PaidReceiptItem> paidReceiptItemSet;
    private Set<FreeReceiptItem> freeReceiptItemSet;
    private BigDecimal total;
    private int discountPercent;
    private String promotionDescription;

    public Receipt(final Receipt receipt) {
        this.paidReceiptItemSet = receipt.getPaidReceiptItemSet();
        this.freeReceiptItemSet = receipt.getFreeReceiptItemSet();
        this.total = receipt.getTotal();
        this.discountPercent = receipt.getDiscountPercent();
        this.promotionDescription = receipt.getPromotionDescription();
    }

}
