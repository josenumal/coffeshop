package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;
    private final ReceiptService receiptService;

    public OrderServiceImpl(final ProductService productService, final ReceiptService receiptService) {
        this.productService = productService;
        this.receiptService = receiptService;
    }

    @Override
    public Receipt placeOrder(final Order order) {

        var productIdList = new ArrayList<>(order.getOrderItems().keySet());
        var productList = productService.findProductsByIds(productIdList);

        return receiptService.createReceipt(order, productList);
    }



}
