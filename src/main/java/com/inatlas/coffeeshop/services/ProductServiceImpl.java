package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByIds(final List<Integer> productIdList) {
        return productRepository.findAllById(productIdList);
    }

}
