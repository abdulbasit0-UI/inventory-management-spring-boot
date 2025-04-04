package com.inventory.inventory_management.Product.Dto;


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
    @Min(value  = 0, message = "Product price must be at least 2 characters long")
    private Double price;
    @NotNull(message = "Product quantity is required")
    @Min(value =  0, message = "Product quantity must be at least 2 characters long")
    private Integer quantity;
    private String description;
    private Long categoryId;
    private Long supplierId; // Add this field for supplier relationship
}
