package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.ProductType;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionFoodAndDrinks implements Promotable {

    private static final float CONDITION_TOTAL_PRICE = 50f;
    private static final int PROMOTION_PRODUCT_ID = 1;
    private static final String PROMOTION_DESCRIPTION = "PromotionFoodAndDrinks";

    @Override
    public PromotionResponse getPromotionResponse(final Order order, final Receipt receipt, List<Product> productList) {

        boolean existsBothTypes = productList.stream()
                .map(Product::getProductType)
                .distinct()
                .allMatch(productType -> productType == ProductType.FOOD || productType == ProductType.DRINKS);

        if (!existsBothTypes || receipt.getTotal() <= CONDITION_TOTAL_PRICE) {
            return new PromotionResponse(false, receipt);
        }

        productList.stream().filter(product -> product.getProductId() == PROMOTION_PRODUCT_ID).forEach(product -> product.setPrice(3f));

        var newReceipt = new Receipt(receipt);
        newReceipt.setPromotionDescription(PROMOTION_DESCRIPTION);

        return new PromotionResponse(true, newReceipt);
    }

}
