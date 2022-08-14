package com.myproject.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myproject.webshop.service.ProductService;

@Controller
public class HomeController{
	
	@Value("${categories}")
	private String[] categories;
	
	@Autowired 
	ProductService productService;
	
	@GetMapping("/")
	public String productCategories(Model model) {
		
		model.addAttribute("numOfBedroomAccessories", productService.countByCategory("Bedroom Accessories"));
		model.addAttribute("numOfBathroomAccessories", productService.countByCategory("Bathroom Accessories"));
		model.addAttribute("numOfVehicles", productService.countByCategory("Vehicles"));
		model.addAttribute("numOfComputers", productService.countByCategory("Computers"));
		model.addAttribute("numOfSport", productService.countByCategory("Sport"));
		model.addAttribute("numOfLaptops", productService.countByCategory("Laptops"));
		model.addAttribute("numOfMobiles", productService.countByCategory("Mobiles"));
		model.addAttribute("numOfTablets", productService.countByCategory("Tablets"));
		model.addAttribute("numOfTools", productService.countByCategory("Tools"));
		model.addAttribute("numOfClothes", productService.countByCategory("Clothes"));
		model.addAttribute("numOfTV", productService.countByCategory("TV"));
		model.addAttribute("numOfKitchenAccessories", productService.countByCategory("Kitchen Accessories"));
		
		return "index";
	}

}
