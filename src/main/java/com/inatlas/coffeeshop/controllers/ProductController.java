package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/product")
public interface ProductController {

    @GetMapping("/available")
    List<ProductDto> getAvailableProducts();

    @GetMapping
    List<ProductDto> listProducts();

    @GetMapping("/{id}")
    ProductDto getProduct(@PathVariable Integer id);

    @PostMapping
    ProductDto createProduct(@RequestBody ProductDto productDto);

    @PutMapping("/{id}")
    ProductDto updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto);

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable Integer id);

}
