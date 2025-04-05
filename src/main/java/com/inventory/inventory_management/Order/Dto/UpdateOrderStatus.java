package com.inventory.inventory_management.Order.Dto;

import com.inventory.inventory_management.Order.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatus {
    @NotNull(message = "Order ID is required")
    private OrderStatus orderStatus;    
}
