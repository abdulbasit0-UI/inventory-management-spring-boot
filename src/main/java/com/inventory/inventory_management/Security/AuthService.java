package com.inventory.inventory_management.Security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.inventory.inventory_management.Security.Dto.AuthRequest;
import com.inventory.inventory_management.Security.Dto.AuthResponse;
import com.inventory.inventory_management.Security.Dto.RegisterRequest;
import com.inventory.inventory_management.Security.jwt.JwtUtils;

import jakarta.transaction.Transactional;

@Service

public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String[] roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toArray(String[]::new);

        return AuthResponse.builder().token(jwt).username(userDetails.getUsername()).roles(roles).build();
    }

    @Transactional
    public User registerUser(RegisterRequest registerUser) {
        if (userRepository.existsByUsername(registerUser.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerUser.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = User.builder().username(registerUser.getUsername())
                .email(registerUser.getEmail())
                .firstName(registerUser.getFirstName())
                .password(encoder.encode(registerUser.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(userRole);

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Transactional
    public User registerAdmin(RegisterRequest registerUser) {
        User user = registerUser(registerUser);

        Set<Role> roles = user.getRoles();

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(adminRole);
        user.setRoles(roles);

        return userRepository.save(user);

    }

}
