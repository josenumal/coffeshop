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
    public List<Product> getAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    @Override
    public List<Product> getAvailableProductsByIds(final List<Integer> productIdList) {
        return productRepository.findAllById(productIdList);
    }

    /**
     * @return
     */
    @Override
    public List<Product> listProducts() {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Product getProduct(final Integer id) {
        return null;
    }

    /**
     * @param product
     * @return
     */
    @Override
    public Product createProduct(final Product product) {
        return null;
    }

    /**
     * @param id
     * @param product
     * @return
     */
    @Override
    public Product updateProduct(final Integer id, final Product product) {
        return null;
    }

    /**
     * @param id
     */
    @Override
    public void removeProduct(final Integer id) {

    }

}
