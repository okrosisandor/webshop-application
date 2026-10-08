package com.myproject.webshop;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myproject.webshop.entity.Customer;
import com.myproject.webshop.entity.CustomerAddress;
import com.myproject.webshop.entity.Role;
import com.myproject.webshop.repository.CustomerRepository;

@SpringBootApplication
public class WebshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initDatabase(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
	    return args -> {
	        String adminEmail = "employee@webshop.com";
	        
	        if (customerRepository.findByEmail(adminEmail) == null) {
	            
	            CustomerAddress adminAddress = new CustomerAddress();
	            adminAddress.setCountry("Hungary");
	            adminAddress.setCity("Budapest");
	            adminAddress.setZipCode("1051");
	            adminAddress.setAddress("Szent István tér 1.");
	            adminAddress.setPhone("+36 1 123 4567");

	            Customer employee = new Customer();
	            employee.setFirstName("System");
	            employee.setLastName("Administrator");
	            employee.setUserName("Employee");
	            employee.setEmail(adminEmail);
	            employee.setPassword(passwordEncoder.encode("admin123"));
	            employee.setEnabled(true);
	            employee.setDateOfRegistration(new Date());

	            employee.setCustomerAddress(adminAddress);

	            employee.setRoles(Arrays.asList(
	                new Role("ROLE_USER"), 
	                new Role("ROLE_EMPLOYEE")
	            ));

	            customerRepository.save(employee);
	        }
	    };
	}
}
