package com.inventory.inventory_management.Security.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
        private String token;
        @Builder.Default
        private String tokenType = "Bearer";
        private String username;
        private String[] roles;
}
