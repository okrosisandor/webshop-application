package com.myproject.webshop.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.webshop.entity.Customer;
import com.myproject.webshop.entity.Product;
import com.myproject.webshop.entity.ProductRating;
import com.myproject.webshop.entity.PurchasedItem;
import com.myproject.webshop.service.CustomerService;
import com.myproject.webshop.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Value("${categories}")
	private String[] categories;
	
	@Autowired 
	ProductService productService;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/upload")
	public String uploadProduct(Model model) {
		Product product = new Product();
		
		model.addAttribute("product", product);
		model.addAttribute("categories", categories);
		
		return "product-upload";
	}
	
	@GetMapping("/products")
	public String showProducts(Model model) {
		
		String keyword = null;
		
		//default values
		return findPaginated(1, "name", "asc", keyword, "", "", "", model);
	}
	
	@GetMapping("/products/{category}")
	public String showProducts(Model model, @PathVariable (value = "category") String category) {
		
		String keyword = null;
		
		//default values
		return findPaginated(1, "name", "asc", keyword, "", "", category, model);
	}
	
	@GetMapping("/displayProducts")
	public String displayProducts(Model model, @Param("keyword") String keyword) {
		
		//default values
		return findPaginated(1, "rating", "desc", keyword, "", "", "", model);
	}
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, 
			@RequestParam("sortDir") String sortDir,
			@Param("keyword") String keyword,
			@RequestParam("minPrice") String min,
			@RequestParam("maxPrice") String max,
			@RequestParam("category") String category,
			Model model) {
		
		int pageSize = 10;
		
		double minPrice = 0;
		double maxPrice = 1000050;
		
		if(min != "") {
			minPrice = Double.parseDouble(min);
		}
		
		if(max != "") {
			maxPrice = Double.parseDouble(max);
		}
		
		Page<Product> page = productService.findPaginated(pageNo, pageSize, sortField, sortDir, keyword, minPrice, maxPrice, category);
		List<Product> products = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("minPrice", (int) minPrice);
		model.addAttribute("maxPrice", (int) maxPrice);
		model.addAttribute("category", category);
		
		model.addAttribute("products", products);
		
		return "products";
		
	}
	
	@GetMapping("/productDetail/{pageNo}")
	public String productDetail(@PathVariable (value = "pageNo") int pageNo, @RequestParam("productId") int id, Model model) {
		
		Product product = productService.findById(id);
		
		int pageSize = 10;
		
		Page<ProductRating> page = productService.getRatings(pageNo, pageSize, product.getId());
		
		List<ProductRating> ratings = page.getContent();
		
		model.addAttribute("product", product);
		model.addAttribute("numberOfReviews", product.getProductRatings().size());
		
		model.addAttribute("averageRating", product.getRating());
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("productRatings", ratings);
		
		return "product-details";
	}


	@PostMapping("/save")
	public String saveProduct(@RequestParam("file") MultipartFile file,
    		@RequestParam("name") String name,
    		@RequestParam("price") double price,
    		@RequestParam("category") String category,
    		@RequestParam("productDetail.description") String desc) {
		
		productService.save(file, name, desc, price, category);
		
		return "redirect:/products/displayProducts";
	}
	
	@GetMapping("/rateProduct")
	public String rateProduct(
			@RequestParam("productId") int productId,
			@RequestParam("customerId") int customerId,
			@RequestParam("rating") int rating, 
			@RequestParam("opinion") String opinion) {
		
		Customer customer = customerService.findById(customerId);
		Product product = productService.findById(productId);
		
		ProductRating productRating = new ProductRating(customer, product);
		productRating.setDateOfRating(new Date());
		productRating.setRating(rating);
		productRating.setOpinion(opinion);
		productRating.setAlreadyRated(true);
		
		customer.addToProductRatings(productRating);
		productService.addToRating(productRating);
		productService.calculateAverage(product);
		
		return "redirect:/customers/user";
	}
	
	@GetMapping("/processed")
	public String processed(@RequestParam("id") int id) {
		
		PurchasedItem item = productService.getPurchasedItem(id);
		
		item.setProcessed(true);
		
		productService.savePurchasedItem(item);
		
		return "redirect:/customers/employee";
	}
	
}
