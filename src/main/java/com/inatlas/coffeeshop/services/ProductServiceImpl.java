package com.inatlas.coffeeshop.services;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    @Override
    public List<Product> getAvailableProductsByIds(final List<Integer> productIdList) {
        return productRepository.findByAvailableTrueAndIdIn(productIdList);
    }

    @Override
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(final Integer id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product updateProduct(final Integer id, final Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(final Integer id) {
        productRepository.deleteById(id);

    }

}
