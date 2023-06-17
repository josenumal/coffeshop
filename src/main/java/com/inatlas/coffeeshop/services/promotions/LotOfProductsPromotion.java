package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LotOfProductsPromotion extends Promotion implements Promotable {

    private static final int CONDITION_PRODUCT_AMOUNT = 8;
    private static final int PROMOTION_DISCOUNT_PERCENT = 5;
    private static final String PROMOTION_DESCRIPTION = "You have a 5% discount on the final price for ordering more than 8 products";

    @Override
    protected boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList) {

        return (order.getOrderItems().values().stream().mapToInt(Integer::intValue).sum()) > CONDITION_PRODUCT_AMOUNT;

    }

    @Override
    protected Receipt buildPromotionReceipt(final Receipt receipt, final List<Product> productList) {

        var newReceipt = new Receipt(receipt);
        newReceipt.setTotal(receipt.getTotal() - (receipt.getTotal() * PROMOTION_DISCOUNT_PERCENT / 100));
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return newReceipt;
    }

}
