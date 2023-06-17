package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAvailableProducts();

    List<Product> findProductsByIds(List<Integer> productIdList);
}
