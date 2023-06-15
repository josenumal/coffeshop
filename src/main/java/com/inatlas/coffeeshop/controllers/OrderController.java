package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/order")
public interface OrderController {

    @PostMapping
    Receipt placeOrder(Order order);

}
