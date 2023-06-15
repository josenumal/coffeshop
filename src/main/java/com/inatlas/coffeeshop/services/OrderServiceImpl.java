package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.models.ReceiptItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;

    public OrderServiceImpl(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Receipt placeOrder(final Order order) {

        var productIdList = new ArrayList<>(order.getOrderItems().keySet());
        var productList = productService.findProductsByIds(productIdList);

        Set<ReceiptItem> receiptItemSet = order.getOrderItems().entrySet().stream().map(
                entry -> createReceiptItem(entry.getKey(), entry.getValue(), productList)
        ).collect(Collectors.toSet());

        return createReceipt(receiptItemSet);
    }

    private ReceiptItem createReceiptItem(final Integer productId, final Integer productAmount, final List<Product> productList) {
        var productToItem = productList.stream().filter(product -> productId == product.getProductId()).findFirst().orElseThrow();
        return new ReceiptItem(productAmount, productToItem.getProductName(), productToItem.getPrice(), productAmount * productToItem.getPrice());
    }

    private Receipt createReceipt(final Set<ReceiptItem> receiptItemSet) {
        var total = (float) receiptItemSet.stream().mapToDouble(ReceiptItem::getTotal).sum();
        return new Receipt(receiptItemSet, total);
    }

}
