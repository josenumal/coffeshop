package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.mappers.ProductMapper;
import com.inatlas.coffeeshop.models.ProductDto;
import com.inatlas.coffeeshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDto> getAvailableProducts() {

        var aa = productRepository.findByAvailableTrue();

        return productMapper.productListToProductDtoList(aa);
    }

    @Override
    public List<Product> findProductsByIds(final List<Integer> productIdList) {
        return productRepository.findAllById(productIdList);
    }

}
