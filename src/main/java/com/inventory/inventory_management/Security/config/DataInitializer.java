package com.inventory.inventory_management.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.inventory.inventory_management.Security.ERole;
import com.inventory.inventory_management.Security.Role;
import com.inventory.inventory_management.Security.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for(ERole role : ERole.values()){
            if(roleRepository.findByName(role).isEmpty()){
                roleRepository.save(new Role(role));
            }
        }
    }
       
}
