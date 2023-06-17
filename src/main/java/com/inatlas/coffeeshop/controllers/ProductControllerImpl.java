package com.inatlas.coffeeshop.controllers;

import com.inatlas.coffeeshop.dto.ProductDto;
import com.inatlas.coffeeshop.mappers.ProductMapper;
import com.inatlas.coffeeshop.services.ProductService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductControllerImpl(final ProductService productService, final ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> getAvailableProducts() {
        return productMapper.productListToProductDtoList(productService.getAvailableProducts());
    }

    @Override
    public List<ProductDto> listProducts() {
        return productMapper.productListToProductDtoList(productService.listProducts());
    }

    @Override
    public ProductDto getProduct(final Integer id) {
        return productMapper.productToProductDto(productService.getProduct(id));
    }

    @Override
    public ProductDto createProduct(final ProductDto productDto) {
        return productMapper.productToProductDto(productService.createProduct(productMapper.productDtoToProduct(productDto)));
    }

    @Override
    public ProductDto updateProduct(final Integer id, final ProductDto productDto) {
        return productMapper.productToProductDto(productService.createProduct(productMapper.productDtoToProduct(productDto)));
    }

    @Override
    public void deleteProduct(final Integer id) {
        productService.deleteProduct(id);
    }

}
