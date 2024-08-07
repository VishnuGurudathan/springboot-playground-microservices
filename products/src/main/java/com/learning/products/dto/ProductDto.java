package com.learning.products.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductDto {

    /**
     * The unique identifier of the product.
     */
    @NotEmpty(message = "ProductId cannot be null or empty")
    @Pattern(regexp="(^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$\n)",message = "AccountNumber must be UUID4 format")
    private String productId;
    // TODO: can think of product id and id as a seperate thing and male id as uuid4 and id as 6 digit number

    /**
     * The name of the product.
     */
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(max = 100, message = "Name cannot be longer than 100 characters")
    private String name;

    /**
     * The description of the product.
     */
    @Size(max = 500, message = "Description cannot be longer than 500 characters")
    private String description;

    /**
     * The price of the product.
     */
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private double price;

    /**
     * The category of the product.
     */
    @NotEmpty(message = "Category cannot be null or empty")
    @Size(max = 50, message = "Category cannot be longer than 50 characters")
    private String category;

    /**
     * The stock quantity of the product.
     */
    @NotNull(message = "Stock quantity cannot be null")
    @Positive(message = "Stock quantity must be positive")
    private int stockQuantity;
}
