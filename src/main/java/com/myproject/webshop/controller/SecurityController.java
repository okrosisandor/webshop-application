package com.myproject.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.webshop.dto.CustomerDto;
import com.myproject.webshop.entity.Customer;

@Controller
public class SecurityController {
	
	@GetMapping("/login")
	public String login(Model model) {
		
		model.addAttribute("showText", "");
		return "login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		
		CustomerDto customerDTO = new CustomerDto();
		
		model.addAttribute("customerDTO", customerDTO);
		
		return "register-form";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied() {
		return "exception/403";
	}
}
