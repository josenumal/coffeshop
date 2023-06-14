package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.entities.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/products")
public interface ProductsController {

    @GetMapping
    List<Product> getProducts();

}
