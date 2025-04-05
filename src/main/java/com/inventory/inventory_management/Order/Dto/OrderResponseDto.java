package com.inventory.inventory_management.Order.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.inventory.inventory_management.Order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long id;
    private String orderNumber;
    private Long orderId;
    private String userEmail;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private String shippingAddress;
    private BigDecimal totalAmount;
    private List<OrderItemDto> orderItems; // Assuming you have an OrderItemResponseDto entity  
}
