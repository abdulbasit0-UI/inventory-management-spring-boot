package com.inventory.inventory_management.Product.View;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.inventory.inventory_management.Category.Category;
import com.inventory.inventory_management.Category.CategoryRepository;
import com.inventory.inventory_management.Category.CategoryService;
import com.inventory.inventory_management.Product.ProductService;
import com.inventory.inventory_management.Product.Dto.ProductRequestDto;
import com.inventory.inventory_management.Product.Dto.ProductResponseDto;
import com.inventory.inventory_management.Supplier.Supplier;
import com.inventory.inventory_management.Supplier.SupplierRepository;
import com.inventory.inventory_management.Supplier.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductView {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<ProductResponseDto> products = productService.getAllProducts();
        model.addAttribute("products", products); // Add the list of products to the model
        return "products"; // Return the name of the HTML file (products.html) in the templates directory
    }

    @GetMapping("/products/create")
    public String showCreateForm(Model model) {
        model.addAttribute("productDto", new ProductRequestDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "products/create";
    }

    @PostMapping("/products/create")
    public String saveProduct(@ModelAttribute("productDto") @Valid ProductRequestDto product,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            return "products/create"; // Return to the form if there are validation errors
        }

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Supplier supplier = supplierRepository.findById(product.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        product.setCategoryId(category.getId());
        product.setSupplierId(supplier.getId());

        productService.createProduct(product);
        return "redirect:/products"; // Redirect to the products list after saving
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products"; // Redirect to the products list after deletion
    }
}
