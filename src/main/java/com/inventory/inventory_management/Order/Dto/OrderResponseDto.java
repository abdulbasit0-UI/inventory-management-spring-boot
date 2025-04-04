package com.inventory.inventory_management.Order.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    
}
