package com.myproject.webshop.controller;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.webshop.Utility;
import com.myproject.webshop.dto.CustomerDto;
import com.myproject.webshop.entity.Customer;
import com.myproject.webshop.entity.CustomerCart;
import com.myproject.webshop.entity.Product;
import com.myproject.webshop.entity.ProductRating;
import com.myproject.webshop.entity.PurchasedItem;
import com.myproject.webshop.entity.StandingPurchasedItem;
import com.myproject.webshop.service.CustomerService;
import com.myproject.webshop.service.ProductService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ProductService productService;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@PostMapping("/save")
	public String saveCustomer(@Valid @ModelAttribute("customerDTO") CustomerDto customerDTO, BindingResult bindingResult, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		if(bindingResult.hasErrors()) {
			return "register-form";
		}
		
		Customer customer = customerDTO.getCustomer();
		
		if(!(customerDTO.getCustomer().getPassword().equals(customerDTO.getPassword2()))) {
			
			bindingResult.rejectValue("customer.password", "error.customerDTO", "Passwords must match.");
			bindingResult.rejectValue("password2", "error.customerDTO", "Passwords must match.");
			
			return "register-form";
		}
		
		customerService.save(customer);
		
		String siteURL = Utility.getSiteURL(request);
		
		customerService.sendVerificationEmail(customer, siteURL);
		
		return "redirect:/register?success";
	}
	
	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model model) {
		boolean isVerified = customerService.verify(code);
		
		return "/login";
	}
	
	@PostMapping("/send")
	public String showForgottenPasswordForm(@RequestParam("theEmail") String email, Model model, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		
		Customer customer = customerService.findByEmail(email);
		
		if(customer == null) {
			model.addAttribute("showText", "Email address does not exist");
			return "login";
		}
		
		model.addAttribute("showText", "Email has been sent to your email. Please check your inbox.");
		
		String siteURL = Utility.getSiteURL(request);
		
		customerService.sendForgottenPasswordEmail(customer, siteURL);
		
		return "login";
	}
	
	

	@GetMapping("/getPassword")
	public String getPassword(@Param("customerId") int id, Model model) {
		
		model.addAttribute("customerId", id);
		model.addAttribute("showText", "");
		
		return "forgotten-password";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam("newPassword") String newPassword, @RequestParam("customerId") int id, Model model) {
		
		if(newPassword.length() < 6) {
			model.addAttribute("showText", "Length must be 6 or more");
			return "forgotten-password";
		}
		
		Customer customer = customerService.findById(id);
		
		customer.setPassword(newPassword);
		
		customerService.updateForgottenPassword(customer);
		
		model.addAttribute("showText", "");
		
		return "redirect:/login?updated";
	}
	
	@GetMapping("/addtocart")
	public String addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpServletRequest request) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
		  String username = ((UserDetails)principal).getUsername();
		  
		  Customer customer = customerService.findByUsername(username);
		  Product product = productService.findById(productId);
		  
		  CustomerCart cart = new CustomerCart(customer, product);
		  cart.setQuantity(quantity);
		  
		  customer.addToCustomerCart(cart);
		  
		  customerService.addToCart(cart);
		}
		
		return getPreviousPageByRequest(request).orElse("/");
	}
	
	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request){
	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
	
	@GetMapping("/removeFromCart")
	public String removeFromCart(@RequestParam("cartId") int cartId, Model model) {
		
		CustomerCart cart = customerService.getCart(cartId);
		
		cart.getCustomer().removeFromCart(cart.getId());
		customerService.removeFromCart(cartId);
		
		return "redirect:/customers/user";
	}
	
	@GetMapping("/purchaseProduct")
	public String purchaseProduct(@RequestParam("cartId") int cartId, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		if (principal instanceof UserDetails) {
			  String username = ((UserDetails)principal).getUsername();
			  
			  CustomerCart cart = customerService.getCart(cartId);
			  
			  Customer customer = customerService.findByUsername(username);
			  Product product = productService.findById(cart.getProduct().getId());
			  
			  PurchasedItem item = new PurchasedItem(customer, product);
			  StandingPurchasedItem standingItem = new StandingPurchasedItem(customer, product);
			  
			  item.setAlreadyPaid(true);
			  item.setOrderDate(new Date());
			  item.setQuantity(cart.getQuantity());
			  
			  standingItem.setAlreadyPaid(true);
			  standingItem.setOrderDate(new Date());
			  standingItem.setQuantity(cart.getQuantity());
			  
			  customer.addToPurchasedItems(item);
			  customer.addToStandingPurchasedItems(standingItem);
			  
			  customerService.addToPurchasedItems(item);
			  customerService.addToStandingPurchasedItems(standingItem);
			  
			  customerService.removeFromCart(cartId);
			}
	
		return "redirect:/customers/user";
	}
	
	
	@GetMapping("/user")
	public String showUserPage(Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		if (principal instanceof UserDetails) {
		  String username = ((UserDetails)principal).getUsername();
		  
		  Customer customer = customerService.findByUsername(username);
		  
//		  customer.addRole("ROLE_EMPLOYEE");
//	  
//		  customer.setPassword("asdasd");
//		  customerService.updateCustomer(customer);
		  
		  model.addAttribute("theCustomer", customer);
		  
		  
		  model.addAttribute("cart", customer.getCustomerCart());
		  model.addAttribute("itemsInCart", customer.getCustomerCart().size());
		  
		  List<PurchasedItem> purchasedItems = customer.getPurchasedItems();
		  Collections.reverse(purchasedItems);
		  
		  model.addAttribute("purchasedItems", purchasedItems);
		  model.addAttribute("totalPurchasedItems", customer.getPurchasedItems().size());
		  
		  String cartTotalPrice = getCartTotal(customer.getCustomerCart());
		  model.addAttribute("cartTotalPrice", cartTotalPrice);
		  
		  List<Integer> ratedProductIds = new ArrayList<>();
		  
		  for(ProductRating rat : customer.getProductRatings()) {
			  ratedProductIds.add(rat.getProduct().getId());
		  }
		  
		  model.addAttribute("ratedProductIds", ratedProductIds);
		}
		
		return "user-page";
	}
	
	private String getCartTotal(List<CustomerCart> customerCart) {
		double cartTotalPrice = 0;
		  
		  for(CustomerCart c : customerCart) {
			  double tempPrice = c.getProduct().getPrice() * c.getQuantity(); 
			  cartTotalPrice += tempPrice;
		  }
		  
		  DecimalFormat df = new DecimalFormat("#.##");
		  
		  return df.format(cartTotalPrice);
	}
	
	@GetMapping("/changeDetails")
	public String changeDetails(
			@RequestParam("customerId") int customerId,
			@RequestParam(value = "firstName", defaultValue = "") String firstName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password1", defaultValue = "") String password,
			@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "zipCode", defaultValue = "") String zipCode,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "phone", defaultValue = "") String phone) {
		
		modifyUserDetails(customerId, firstName, lastName, email, password, country, city, zipCode, address, phone);
		
		return "redirect:/customers/user";
	}

	private void modifyUserDetails(int customerId, String firstName, String lastName, String email, String password,
			String country, String city, String zipCode, String address, String phone) {


		Customer customer = customerService.findById(customerId);
		
		if(firstName != null) {
			customer.setFirstName(firstName);
		}
		
		if(lastName != null) {
			customer.setLastName(lastName);
		}
		
		if(email != null) {
			customer.setEmail(email);
		}
		
		if(password != null) {
			customer.setPassword(password);
			//customerService.updateForgottenPassword(customer);
		}
		
		if(country != null) {
			customer.getCustomerAddress().setCountry(country);
		}
		
		if(city != null) {
			customer.getCustomerAddress().setCity(city);
		}
		
		if(zipCode != null) {
			customer.getCustomerAddress().setZipCode(zipCode);
		}
		
		if(address != null) {
			customer.getCustomerAddress().setAddress(address);
		}
		
		if(phone != null) {
			customer.getCustomerAddress().setPhone(phone);
		}
		
		customerService.updateCustomer(customer);
		
	}

	@GetMapping("/employee")
	public String showEmployeePage(Model model) {
		
		List<PurchasedItem> standingItems = customerService.getPurchasedItems();
		List<PurchasedItem> previousOrders = new ArrayList<PurchasedItem>();
		
		for(int i = 0; i < standingItems.size(); i++) {
			if(standingItems.get(i).isProcessed()) {
				previousOrders.add(standingItems.get(i));
				standingItems.remove(i);
				i--;
			}
		}
		
		Collections.reverse(previousOrders);
		
		model.addAttribute("standingProducts", standingItems);
		model.addAttribute("previousOrders", previousOrders);
		
		List<PurchasedItem> today = productService.todaysOrders();
		List<PurchasedItem> thisMonth = productService.thisMonthsOrders();
		List<PurchasedItem> total = customerService.getPurchasedItems();
		
		model.addAttribute("todaysVisitors", formatNumber(customerService.todaysVisitors().size()));
		model.addAttribute("thisMonthsVisitors", formatNumber(customerService.thisMonthsVisitors().size()));
		model.addAttribute("totalVisitors", formatNumber(customerService.totalVisitors().size()));
		
		model.addAttribute("todaysRegistrations", formatNumber(customerService.todayRegistrations().size()));
		model.addAttribute("thisMonthsRegistrations", formatNumber(customerService.thisMonthsRegistrations().size()));
		model.addAttribute("totalRegistrations", formatNumber(customerService.findAll().size()));
		
		model.addAttribute("todaysOrders", formatNumber(today.size()));
		model.addAttribute("thisMonthsOrders", formatNumber(thisMonth.size()));
		model.addAttribute("totalOrders", formatNumber(total.size()));
		
		model.addAttribute("todaysIncome", formatNumber((int) calculateIncome(today)));
		model.addAttribute("thisMonthsIncome", formatNumber((int) calculateIncome(thisMonth)));
		model.addAttribute("totalIncome", formatNumber((int) calculateIncome(total)));
		
		return "employee-page";
	}

	private double calculateIncome(List<PurchasedItem> items) {
		
		double amount = 0;
		
		for(PurchasedItem item : items) {
			double temp = item.getQuantity() * item.getProduct().getPrice();
			amount += temp;
		}
		
		return amount;
	}

	private String formatNumber(int number) {
		
		if(number >= 10000000) {
			return String.format("%.3f", ((double) number / 1000000)) + "m";
		}
		
		if(number >= 1000000) {
			return String.valueOf(number / 1000000 + "," + ((number % 1000000) / 1000) + "," + number % 1000);
		}
		
		
		if(number >= 1000) {
			return String.valueOf(number / 1000 + "," + number % 1000) + "k";
			
		}
		
		return String.valueOf(number);
		
	}
	
}
