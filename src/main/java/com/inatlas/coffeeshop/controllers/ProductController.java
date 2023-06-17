package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.models.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/product")
public interface ProductController {

    @GetMapping("/available")
    List<ProductDto> getAvailableProducts();

}
