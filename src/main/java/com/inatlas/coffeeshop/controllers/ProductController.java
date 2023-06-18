package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/product")
public interface ProductController {

    @GetMapping("/available")
    @SuppressWarnings("unused")
    List<ProductDto> getAvailableProducts();

    @GetMapping
    @SuppressWarnings("unused")
    List<ProductDto> listProducts();

    @GetMapping("/{id}")
    @SuppressWarnings("unused")
    ProductDto getProduct(@PathVariable Integer id);

    @PostMapping
    @SuppressWarnings("unused")
    ProductDto createProduct(@RequestBody ProductDto productDto);

    @PutMapping("/{id}")
    @SuppressWarnings("unused")
    ProductDto updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto);

    @DeleteMapping("/{id}")
    @SuppressWarnings("unused")
    void deleteProduct(@PathVariable Integer id);

}
