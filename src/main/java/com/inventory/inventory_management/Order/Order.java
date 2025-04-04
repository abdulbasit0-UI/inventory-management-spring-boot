package com.inventory.inventory_management.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.inventory.inventory_management.Security.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Assuming you have a User entity

    @Column(nullable = false)
    private LocalDateTime orderDate;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    private String shippingAddress;

    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "order", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems; // Assuming you have an OrderItem entity

}


