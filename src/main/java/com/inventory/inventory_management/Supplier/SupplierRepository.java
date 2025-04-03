package com.inventory.inventory_management.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // Custom query methods can be defined here if needed
    // For example, find by name or email, etc.

}
