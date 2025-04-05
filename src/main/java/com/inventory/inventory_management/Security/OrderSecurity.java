
package com.inventory.inventory_management.Security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.inventory.inventory_management.Order.Order;
import com.inventory.inventory_management.Order.OrderRepository;

@Component
public class OrderSecurity {
    
    private final OrderRepository orderRepository;
    
    public OrderSecurity(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public boolean isOrderOwner(Long orderId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return false;
        }
        
        User user = (User) authentication.getPrincipal();
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        return orderOpt.isPresent() && orderOpt.get().getUser().getId().equals(user.getId());
    }
}