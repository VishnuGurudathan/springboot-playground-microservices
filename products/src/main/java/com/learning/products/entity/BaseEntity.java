package com.learning.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * <p>
 * BaseEntity is a mapped superclass that provides common auditing fields for entity classes.
 * It includes fields for tracking the creation and last modification timestamps and the users
 * responsible for these actions.
 * </p>
 * Annotations:
 * - @MappedSuperclass: Indicates that this class is a base class whose properties will be inherited by subclasses.
 * - @EntityListeners(AuditingEntityListener.class): Specifies that the AuditingEntityListener should be used to populate auditing fields.
 * - Lombok annotations (@Getter, @Setter, @ToString): Automatically generate getter, setter, and toString methods.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @ToString
public class BaseEntity {

    /**
     * <p>The timestamp when the entity was created.</p>
     *
     * Annotations:
     * - @CreatedDate: Indicates that this field should be populated with the creation timestamp.
     * - @Column(updatable = false): Specifies that this column is not updatable.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * <p>The user who created the entity.</p>
     *
     * Annotations:
     * - @CreatedBy: Indicates that this field should be populated with the user who created the entity.
     * - @Column(updatable = false): Specifies that this column is not updatable.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * <p>The timestamp when the entity was last updated.</p>
     *
     * Annotations:
     * - @LastModifiedDate: Indicates that this field should be populated with the last modification timestamp.
     * - @Column(insertable = false): Specifies that this column is not insertable.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    /**
     * <p>The user who last updated the entity. <p>
     *
     * Annotations:
     * - @LastModifiedBy: Indicates that this field should be populated with the user who last updated the entity.
     * - @Column(insertable = false): Specifies that this column is not insertable.
     */
    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}