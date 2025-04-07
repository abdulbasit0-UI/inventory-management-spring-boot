package com.inventory.inventory_management.Category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inventory.inventory_management.Category.Dto.CategoryRequestDto;
import com.inventory.inventory_management.Category.Dto.CategoryResponseDto;
import com.inventory.inventory_management.Category.Dto.CategoryUpdateDto;
import com.inventory.inventory_management.Product.Product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDto(savedCategory.getId(), savedCategory.getName(), List.of());
    }


    @Transactional
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryResponseDto(category.getId(), category.getName(), category.getProducts().stream().map(Product::getName).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponseDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryResponseDto(category.getId(), category.getName(), category.getProducts().stream().map(Product::getName).collect(Collectors.toList()));
    }

    @Transactional CategoryResponseDto updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryUpdateDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return new CategoryResponseDto(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getProducts().stream().map(Product::getName).collect(Collectors.toList()));
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
