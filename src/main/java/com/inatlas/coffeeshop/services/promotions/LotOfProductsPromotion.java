package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@SuppressWarnings("unused")
public class LotOfProductsPromotion extends Promotion implements Promotable {

    private static final int CONDITION_PRODUCT_AMOUNT = 8;
    private static final BigDecimal PROMOTION_DISCOUNT_PERCENT = new BigDecimal("0.05");
    private static final String PROMOTION_DESCRIPTION = "You have a 5% discount on the final price for ordering more than 8 products";

    @Override
    protected boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList) {

        return (order.getOrderItems().values().stream().mapToInt(Integer::intValue).sum()) > CONDITION_PRODUCT_AMOUNT;

    }

    @Override
    protected Receipt buildPromotionReceipt(final Order order, final Receipt receipt, final List<Product> productList) {

        var newReceipt = new Receipt(receipt);
        var total = receipt.getTotal().subtract(receipt.getTotal().multiply(PROMOTION_DISCOUNT_PERCENT)).setScale(2, RoundingMode.HALF_UP);
        newReceipt.setTotal(total);
        newReceipt.setDiscountPercent(PROMOTION_DISCOUNT_PERCENT);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return newReceipt;
    }

}
