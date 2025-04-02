package com.inventory.inventory_management.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventory.inventory_management.Category.Category;
import com.inventory.inventory_management.Category.CategoryRepository;
import com.inventory.inventory_management.Product.Dto.ProductRequestDto;
import com.inventory.inventory_management.Product.Dto.ProductResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        Optional<Category> category = categoryRepository.findById(productRequestDto.getCategoryId());

        if(category.isEmpty()) {
            throw new RuntimeException("Category not found");
        };

        Category category1 = category.get();
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantity(productRequestDto.getQuantity());
        product.setDescription(productRequestDto.getDescription());
        product.setCategory(category1);
        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getQuantity(), savedProduct.getDescription());
    } 

    @Transactional List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getDescription())).toList();
    }

}
