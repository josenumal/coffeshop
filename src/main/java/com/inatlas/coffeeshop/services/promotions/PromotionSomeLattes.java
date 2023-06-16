package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.FreeReceiptItem;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PromotionSomeLattes implements Promotable {

    private static final int CONDITION_PRODUCT_ID = 1;
    private static final int CONDITION_PRODUCT_AMOUNT = 2;
    private static final String PROMOTION_PRODUCT_NAME = "Espresso";
    private static final String PROMOTION_DESCRIPTION = "PromotionSomeLattes";

    @Override
    public PromotionResponse getPromotionResponse(final Order order, final Receipt receipt, List<Product> productList) {

        var latteAmount = order.getOrderItems().entrySet().stream()
                .filter(entry -> entry.getKey().equals(CONDITION_PRODUCT_ID))
                .mapToInt(Map.Entry::getValue)
                .sum();

        if (latteAmount < CONDITION_PRODUCT_AMOUNT) {
            return new PromotionResponse(false, receipt);
        }

        var freeReceiptItemSet = Set.of(new FreeReceiptItem(latteAmount / CONDITION_PRODUCT_AMOUNT, PROMOTION_PRODUCT_NAME));
        var newReceipt = new Receipt(receipt);
        newReceipt.setFreeReceiptItemSet(freeReceiptItemSet);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return new PromotionResponse(true, newReceipt);
    }

}
