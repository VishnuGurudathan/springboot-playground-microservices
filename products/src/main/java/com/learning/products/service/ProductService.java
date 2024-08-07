package com.learning.products.service;

import com.learning.products.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * Create a new product.
     * 
     * @param productDto The product to create.
     * @return The created product.
     */
    ProductDto createProduct(ProductDto productDto);

    /**
     * Update an existing product.
     * 
     * @param productDto The product to update.
     * @return The updated product.
     */
    ProductDto updateProduct(ProductDto productDto);

    /**
     * Delete a product by its ID.
     * 
     * @param productId The ID of the product to delete.
     */
    void deleteProduct(String productId);

    /**
     * Retrieve a product by its ID.
     * 
     * @param productId The ID of the product to retrieve.
     * @return A retrieved product, or empty if not found.
     */
    ProductDto getProductById(String productId);

    /**
     * Retrieve all ProductDto.
     * 
     * @return A Page of all ProductDto.
     */
    Page<ProductDto> getAllProducts(Pageable pageable);
}