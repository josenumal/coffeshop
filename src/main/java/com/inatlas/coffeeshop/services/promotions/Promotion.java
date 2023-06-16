package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;

import java.util.List;

public abstract class Promotion {

    protected abstract boolean isPromotionApplicable(final Order order, final Receipt receipt, List<Product> productList);

    protected abstract Receipt buildPromotionReceipt(final Receipt receipt, final List<Product> productList);

    public PromotionResponse getPromotionResponse(final Order order, final Receipt receipt, List<Product> productList) {

        if (isPromotionApplicable(order, receipt, productList)) {
            return new PromotionResponse(true, buildPromotionReceipt(receipt, productList));
        }

        return new PromotionResponse(false, receipt);
    }






}
