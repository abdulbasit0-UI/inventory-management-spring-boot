package com.inventory.inventory_management.Product.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
        private Long id;
        private String name;
        private Double price;
        private Integer quantity;
        private String description;
}
