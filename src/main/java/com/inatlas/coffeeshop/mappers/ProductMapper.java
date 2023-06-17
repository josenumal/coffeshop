package com.inatlas.coffeeshop.mappers;

import com.inatlas.coffeeshop.entities.Product;
import com.inatlas.coffeeshop.models.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    List<ProductDto> productListToProductDtoList(List<Product> productList);

    Product productDtoToProduct(ProductDto productDto);

    List<Product> productDtoListToProductList(List<ProductDto> productDtoList);

}
