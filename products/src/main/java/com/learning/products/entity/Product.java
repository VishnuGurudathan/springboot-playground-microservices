package com.learning.products.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

/**
 * Product entity class that represents a product in the system.
 * Inherits common auditing fields from BaseEntity.
 */
@Entity
@Table(name = "products")
@Getter @Setter @ToString
public class Product extends BaseEntity {

    /**
     * The primary key of the product.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String productId;

    /**
     * The name of the product.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The description of the product.
     */
    @Column
    private String description;

    /**
     * The price of the product.
     */
    @Column(nullable = false)
    private double price;

    /**
     * The category of the product.
     */
    @Column
    private String category;

    /**
     * The stock quantity of the product.
     */
    @Column(nullable = false)
    private int stockQuantity;
}
