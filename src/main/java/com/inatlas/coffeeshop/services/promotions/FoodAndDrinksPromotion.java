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
    private static final String PROMOTION_DESCRIPTION = "FoodAndDrinksPromotion";

    @Override
    protected boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList) {

        boolean existsBothTypes = productList.stream()
                .map(Product::getProductType)
                .anyMatch(productType -> productType == ProductType.FOOD)
                && productList.stream()
                .map(Product::getProductType)
                .anyMatch(productType -> productType == ProductType.DRINKS);

        return existsBothTypes && receipt.getTotal() <= CONDITION_TOTAL_PRICE;

    }

    @Override
    protected Receipt buildPromotionReceipt(final Receipt receipt, final List<Product> productList) {
        productList.stream().filter(product -> product.getProductId() == PROMOTION_PRODUCT_ID).forEach(product -> product.setPrice(3f));

        var newReceipt = new Receipt(receipt);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return newReceipt;
    }

}
