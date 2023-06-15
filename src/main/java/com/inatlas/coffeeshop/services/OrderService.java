package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;

public interface OrderService {

    Receipt placeOrder(Order order);
}
