package com.inventory.inventory_management.Views;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Return the name of the HTML file (index.html) in the templates directory
    }
}
