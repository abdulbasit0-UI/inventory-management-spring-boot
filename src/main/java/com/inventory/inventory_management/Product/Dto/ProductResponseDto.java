package com.inventory.inventory_management.Product.Dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer quantity;
        private String description;
        private String supplierName;
        private String categoryName;
}
