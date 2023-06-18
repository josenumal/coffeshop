package com.inatlas.coffeeshop.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FreeReceiptItem {

    private int amount;
    private String productName;

}
