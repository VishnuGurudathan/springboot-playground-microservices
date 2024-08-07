package com.learning.products.service.impl;

import com.learning.products.dto.ProductDto;
import com.learning.products.entity.Product;
import com.learning.products.exception.ResourceNotFoundException;
import com.learning.products.mapper.ProductMapper;
import com.learning.products.repository.ProductRepository;
import com.learning.products.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        return null;
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );
        return ProductMapper.toProductDto(product, new ProductDto());
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductMapper::toProductDto);
    }
}
