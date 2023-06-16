package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionLotOfProducts implements Promotable {

    private static final int CONDITION_PRODUCT_AMOUNT = 8;
    private static final int PROMOTION_DISCOUNT_PERCENT = 5;
    private static final String PROMOTION_DESCRIPTION = "PromotionLotOfProducts";

    @Override
    public PromotionResponse getPromotionResponse(final Order order, final Receipt receipt, List<Product> productList) {

        var productAmount = order.getOrderItems().values().stream().mapToInt(Integer::intValue).sum();

        if (productAmount <= CONDITION_PRODUCT_AMOUNT) {
            return new PromotionResponse(false, receipt);
        }

        var newReceipt = new Receipt(receipt);
        newReceipt.setTotal(receipt.getTotal() - (receipt.getTotal() * PROMOTION_DISCOUNT_PERCENT / 100));
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return new PromotionResponse(true, newReceipt);
    }

}
