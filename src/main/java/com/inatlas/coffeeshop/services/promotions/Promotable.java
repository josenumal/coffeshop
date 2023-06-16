package com.inatlas.coffeeshop.services.promotions;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.Order;
import com.inatlas.coffeeshop.models.PromotionResponse;
import com.inatlas.coffeeshop.models.Receipt;

import java.util.List;

public interface Promotable {

    PromotionResponse getPromotionResponse(Order order, Receipt receipt, List<Product> productList);

}
