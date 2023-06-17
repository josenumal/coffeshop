package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.models.ProductDto;
import com.inatlas.coffeeshop.services.ProductService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    public ProductControllerImpl(final ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ProductDto> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

}
