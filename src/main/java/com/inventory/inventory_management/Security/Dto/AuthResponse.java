package com.inventory.inventory_management.Security.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
        private String token;
        private String tokenType = "Bearer";
        private String username;
        private String[] roles;
}
