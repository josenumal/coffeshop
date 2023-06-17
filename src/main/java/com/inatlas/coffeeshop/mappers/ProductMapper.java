package com.inatlas.coffeeshop.mappers;

import com.inatlas.coffeeshop.dto.ProductDto;
import com.inatlas.coffeeshop.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    List<ProductDto> productListToProductDtoList(List<Product> productList);

    Product productDtoToProduct(ProductDto productDto);

    List<Product> productDtoListToProductList(List<ProductDto> productDtoList);

}
