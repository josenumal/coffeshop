package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.ProductType;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodAndDrinksPromotion extends Promotion implements Promotable {

    private static final float CONDITION_TOTAL_PRICE = 50f;
    private static final int PROMOTION_PRODUCT_ID = 1;
    private static final String PROMOTION_DESCRIPTION = "Since your order is over $50 including food and drinks, each latte costs you $3";

    @Override
    protected boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList) {

        return receipt.getTotal() > CONDITION_TOTAL_PRICE &&
                productList.stream().anyMatch(productType -> productType.getId() == PROMOTION_PRODUCT_ID) &&
                productList.stream().anyMatch(productType -> productType.getProductType() == ProductType.FOOD) &&
                productList.stream().anyMatch(productType -> productType.getProductType() == ProductType.DRINKS);

    }

    @Override
    protected Receipt buildPromotionReceipt(final Receipt receipt, final List<Product> productList) {
        productList.stream().filter(product -> product.getId() == PROMOTION_PRODUCT_ID).forEach(product -> product.setPrice(3f));

        var newReceipt = new Receipt(receipt);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return newReceipt;
    }

}
