package com.inatlas.coffeeshop.models;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Order {

    private Map<Integer, Integer> orderItems;

}
