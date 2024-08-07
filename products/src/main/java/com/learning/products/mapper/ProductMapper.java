package com.learning.products.mapper;

import com.learning.products.dto.ProductDto;
import com.learning.products.entity.Product;

public class ProductMapper {

    public static ProductDto toProductDto(Product product, ProductDto productDto) {
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());

        return productDto;
    }

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());

        return productDto;
    }
}
