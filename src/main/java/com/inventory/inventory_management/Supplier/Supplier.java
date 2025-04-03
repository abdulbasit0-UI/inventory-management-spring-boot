package com.inventory.inventory_management.Supplier;

import java.util.Set;

import com.inventory.inventory_management.Product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private String contactPerson;

    private String address;
    private String phoneNumber;
    private String email;

    @ManyToMany(mappedBy = "suppliers", fetch = jakarta.persistence.FetchType.LAZY)
    private Set<Product> products;
}
