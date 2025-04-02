package com.inventory.inventory_management.Category.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotBlank(message = "Category name is required")
    @Size(min = 2, message = "Category name must be at least 2 characters long")
    private String name;

    
}
