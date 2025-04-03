package com.inventory.inventory_management.Supplier.Dto;

import lombok.Data;

@Data
public class SupplierRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String contactPerson;
    
}
