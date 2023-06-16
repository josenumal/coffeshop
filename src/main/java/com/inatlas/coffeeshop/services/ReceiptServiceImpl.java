package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.models.ReceiptItem;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    public Receipt createReceipt(final Order order, final List<Product> productList) {

        var receiptItemSet = order.getOrderItems().entrySet().stream().map(
                entry -> createReceiptItem(entry.getKey(), entry.getValue(), productList)
        ).collect(Collectors.toSet());

        var total = (float) receiptItemSet.stream().mapToDouble(ReceiptItem::getTotal).sum();
        return new Receipt(receiptItemSet, Collections.emptySet(), total, 0);
    }

    private ReceiptItem createReceiptItem(final Integer productId, final Integer productAmount, final List<Product> productList) {
        var productToItem = productList.stream().filter(product -> productId == product.getProductId()).findFirst().orElseThrow();
        return new ReceiptItem(productAmount, productToItem.getProductName(), productToItem.getPrice(), productAmount * productToItem.getPrice());
    }

}