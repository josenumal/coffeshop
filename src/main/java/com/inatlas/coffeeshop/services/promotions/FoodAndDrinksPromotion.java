package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.ProductType;
import com.inatlas.coffeeshop.models.Receipt;
import com.inatlas.coffeeshop.services.ReceiptService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@SuppressWarnings("unused")
public class FoodAndDrinksPromotion extends Promotion implements Promotable {

    private final ReceiptService receiptService;

    private static final BigDecimal CONDITION_TOTAL_PRICE = new BigDecimal("50");
    private static final int PROMOTION_PRODUCT_ID = 1;
    private static final String PROMOTION_DESCRIPTION = "Since your order is over $50 including food and drinks, each latte costs you $3";

    public FoodAndDrinksPromotion(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Override
    protected boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList) {

        return receipt.getTotal().compareTo(CONDITION_TOTAL_PRICE) > 0 &&
                productList.stream().anyMatch(product -> product.getId() == PROMOTION_PRODUCT_ID) &&
                productList.stream().anyMatch(product -> product.getProductType() == ProductType.FOOD) &&
                productList.stream().anyMatch(product -> product.getProductType() == ProductType.DRINKS);

    }

    @Override
    protected Receipt buildPromotionReceipt(final Order order, final Receipt receipt, final List<Product> productList) {
        productList.stream().filter(product -> product.getId() == PROMOTION_PRODUCT_ID).forEach(product -> product.setPrice(new BigDecimal("3")));

        var newReceipt = receiptService.createReceipt(order, productList);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return newReceipt;
    }

}
