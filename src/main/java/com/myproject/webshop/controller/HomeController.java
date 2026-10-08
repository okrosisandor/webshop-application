package com.myproject.webshop.controller;

import com.myproject.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Value("${categories}")
    private String[] categories;

    @Autowired 
    private ProductService productService;

    @GetMapping("/")
    public String productCategories(Model model) {
        Map<String, Long> categoryCounts = new LinkedHashMap<>();
        
        for (String cat : categories) {
            categoryCounts.put(cat, productService.countByCategory(cat));
        }
        
        model.addAttribute("categoryCounts", categoryCounts);
        return "index";
    }
}