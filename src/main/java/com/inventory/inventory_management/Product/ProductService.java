package com.inventory.inventory_management.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.inventory_management.Category.Category;
import com.inventory.inventory_management.Category.CategoryRepository;
import com.inventory.inventory_management.Product.Dto.ProductRequestDto;
import com.inventory.inventory_management.Product.Dto.ProductResponseDto;
import com.inventory.inventory_management.Supplier.Supplier;
import com.inventory.inventory_management.Supplier.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {

        Optional<Category> category = categoryRepository.findById(productRequestDto.getCategoryId());
        Optional<Supplier> supplier = supplierRepository.findById(productRequestDto.getSupplierId());
        if (supplier.isEmpty()) {
            throw new RuntimeException("Supplier not found");
        }
        ;

        if (category.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        ;

        Category category1 = category.get();
        Supplier supplier1 = supplier.get();
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantity(productRequestDto.getQuantity());
        product.setDescription(productRequestDto.getDescription());
        product.setCategory(category1);
        product.setSupplier(supplier1);

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(),
                savedProduct.getQuantity(), savedProduct.getDescription(), savedProduct.getSupplier().getName());
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponseDto(product.getId(), product.getName(),
                product.getPrice(), product.getQuantity(), product.getDescription(), product.getSupplier().getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getQuantity(),
                product.getDescription(), product.getSupplier().getName());
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
