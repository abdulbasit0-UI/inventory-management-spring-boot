package com.inventory.inventory_management.Category.Dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
    private List<String> productNames;
}
