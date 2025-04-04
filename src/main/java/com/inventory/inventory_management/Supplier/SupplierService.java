package com.inventory.inventory_management.Supplier;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.inventory_management.Supplier.Dto.SupplierRequest;
import com.inventory.inventory_management.Supplier.Dto.SupplierResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers() {
        
        return supplierRepository.findAll()
                .stream()
                .map(supplier -> modelMapper.map(supplier, SupplierResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierResponse getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(supplier -> modelMapper.map(supplier, SupplierResponse.class))
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @Transactional
    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found");
        }
        supplierRepository.deleteById(id);
    }


    @Transactional
    public SupplierResponse createSupplier(SupplierRequest supplierRequest) {
        if (supplierRepository.existsByName(supplierRequest.getName())) {
            throw new RuntimeException("Supplier already exists");
        }

        Supplier supplier = modelMapper.map(supplierRequest, Supplier.class);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return modelMapper.map(savedSupplier, SupplierResponse.class);
    }
}
