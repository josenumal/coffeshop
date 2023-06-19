package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.exceptions.BadOrderException;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    public OrderControllerImpl(final OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Receipt placeOrder(@RequestBody final Order order) {
        try {
            return orderService.placeOrder(order);
        } catch (BadOrderException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);

        }
    }
}
