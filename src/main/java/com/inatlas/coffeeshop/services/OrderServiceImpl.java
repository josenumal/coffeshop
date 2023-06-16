package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.FreeReceiptItem;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.promotions.Promotable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductService productService;
    private final ReceiptService receiptService;
    private final List<Promotable> promotableList;

    public OrderServiceImpl(final ProductService productService, final ReceiptService receiptService, final List<Promotable> promotableList) {
        this.productService = productService;
        this.receiptService = receiptService;
        this.promotableList = promotableList;
    }

    @Override
    public Receipt placeOrder(final Order order) {

        var productIdList = new ArrayList<>(order.getOrderItems().keySet());
        var productList = productService.findProductsByIds(productIdList);
        var noPromotionReceipt = receiptService.createReceipt(order, productList);

        var promotionReceiptList = getApplicablePromotionReceipt(order, noPromotionReceipt, productList);

        if (promotionReceiptList.isEmpty()) {
            return noPromotionReceipt;
        }

        return findBestPromotionReceipt(promotionReceiptList);
    }

    private List<Receipt> getApplicablePromotionReceipt(final Order order, final Receipt noPromotionReceipt, final List<Product> productList) {

        return promotableList.stream()
                .map(promotable -> promotable.getPromotionResponse(order, noPromotionReceipt, productList))
                .collect(Collectors.toList()).stream()
                .filter(PromotionResponse::isApplicable)
                .map(PromotionResponse::getReceipt)
                .collect(Collectors.toList());
    }

    private Receipt findBestPromotionReceipt(final List<Receipt> receiptList) {

        var chepeastReceiptList = receiptList.stream()
                .collect(Collectors.groupingBy(Receipt::getTotal))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();

        if (chepeastReceiptList.size() == 1) {
            return chepeastReceiptList.get(0);
        }

        var moreProductsReceiptList = chepeastReceiptList.stream()
                .collect(Collectors.groupingBy(receipt -> receipt.getFreeReceiptItemSet().stream().mapToInt(FreeReceiptItem::getAmount).sum()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();

        return moreProductsReceiptList.get(0);

    }

}
