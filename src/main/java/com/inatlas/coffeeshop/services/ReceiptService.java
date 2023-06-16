package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;

import java.util.List;

public interface ReceiptService {

    Receipt createReceipt(final Order order, final List<Product> productList);
}
