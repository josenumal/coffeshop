package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProducts();

    List<Product> findProductsByIds(List<Integer> productIdList);
}
