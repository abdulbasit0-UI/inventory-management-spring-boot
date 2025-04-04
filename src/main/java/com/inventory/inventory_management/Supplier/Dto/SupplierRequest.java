package com.inventory.inventory_management.Supplier.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {
    @NotBlank(message = "Supplier name is required")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    private String phoneNumber;
    private String address;
    private String contactPerson;

}
