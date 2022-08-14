package com.myproject.webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.webshop.entity.CustomerAddress;
import com.myproject.webshop.repository.CustomerAddressRepository;

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService{
	
	@Autowired
	private CustomerAddressRepository customerAddressRepository;

	@Override
	public void save(CustomerAddress customerAddress) {
		customerAddressRepository.save(customerAddress);
	}

}
