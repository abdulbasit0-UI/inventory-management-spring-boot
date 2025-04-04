package com.inventory.inventory_management.Supplier;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventory_management.Supplier.Dto.SupplierRequest;
import com.inventory.inventory_management.Supplier.Dto.SupplierResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final static Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupplierResponse> createSupplier(@Valid @RequestBody SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.createSupplier(supplierRequest);
        return ResponseEntity.ok(supplierResponse);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MANAGER')")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        logger.debug("Fetching all suppliers");
        try {
            List<SupplierResponse> supplierResponses = supplierService.getAllSuppliers();
            logger.info("Fetched all suppliers successfully");
            return ResponseEntity.ok(supplierResponses);
        } catch (Exception e) {
            logger.error("Error fetching suppliers: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MANAGER')")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id) {
        SupplierResponse supplierResponse = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplierResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

}
