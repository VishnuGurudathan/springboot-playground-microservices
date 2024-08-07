package com.learning.products.controller;

import com.learning.products.dto.ProductDto;
import com.learning.products.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping(path = "/products")
    @PreAuthorize("hasRole('PRODUCT_VIEWER2')")
    ResponseEntity<Page<ProductDto>> fetchAllProducts(@RequestHeader Map<String, String> headers, @SortDefault(sort = "createdAt") @PageableDefault(size = 5) final Pageable pageable) {
        headers.forEach((key, value) -> {
            log.info("Header '{}': {}", key, value);
        });
        log.info(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ProductDto> productDtoPage = productService.getAllProducts(pageable);
        log.error("products received successfully");
        return ResponseEntity.ok(productDtoPage);
    }

    @GetMapping(path = "/products/{productId}")
    ResponseEntity<ProductDto> fetchProductById(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

}
