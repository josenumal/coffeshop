package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAvailableProducts();

    List<Product> getAvailableProductsByIds(List<Integer> productIdList);

    List<Product> listProducts();

    Product getProduct(Integer id);

    Product createProduct(Product product);

    Product updateProduct(Integer id, Product product);

    void removeProduct(Integer id);
}
