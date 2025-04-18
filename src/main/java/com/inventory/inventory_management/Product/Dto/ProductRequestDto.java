package com.inventory.inventory_management.Product.Dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Product name must be at least 2 characters long")
    private String name;
    @NotNull(message = "Product price is required")
    @Min(value = 0, message = "Product price must be at least 2 characters long")
    private BigDecimal price;
    @NotNull(message = "Product quantity is required")
    @Min(value = 0, message = "Product quantity must be at least be 0")
    private Integer quantity;

    private String description;
    @NotNull(message = "Category  is required")
    private Long categoryId;
    @NotNull(message = "Supplier is required")
    private Long supplierId; // Add this field for supplier relationship
}
