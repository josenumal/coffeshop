package com.inatlas.coffeeshop.mappers;

import com.inatlas.coffeeshop.dtos.ProductDto;
import com.inatlas.coffeeshop.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface ProductMapper {

    @Mapping(target = "productName", source = "productName")
    ProductDto productToProductDto(Product product);

    List<ProductDto> productListToProductDtoList(List<Product> productList);

    Product productDtoToProduct(ProductDto productDto);

}
