package com.myproject.webshop.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myproject.webshop.entity.Customer;

public class CustomerDto {
	
	@Valid
	private Customer customer;
	
	@NotNull(message = "field is required")
	@Size(min = 6, message = "length must be 6 or more")
	private String password2;

	public CustomerDto() {
		
	}

	public CustomerDto(@Valid Customer customer,
			@NotNull(message = "field is required") @Size(min = 6, message = "length must be 6 or more") String password2) {
		super();
		this.customer = customer;
		this.password2 = password2;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
