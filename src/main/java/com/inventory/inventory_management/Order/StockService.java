package com.inventory.inventory_management.Order;

import org.springframework.stereotype.Service;

import com.inventory.inventory_management.Product.Product;
import com.inventory.inventory_management.Product.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {
    private final ProductRepository productRepository;

    @Transactional
    public void reduceQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        if(product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available for product: " + productId);
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Transactional
    public void increaseQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    @Transactional
    public boolean isInStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getQuantity() >= quantity;
    }
    
}
