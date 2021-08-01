package com.myproject.webshop.service;

import org.springframework.stereotype.Service;

import com.myproject.webshop.entity.CustomerAddress;

@Service
public interface CustomerAddressService {
	
	public void save(CustomerAddress customerAddress);
}
