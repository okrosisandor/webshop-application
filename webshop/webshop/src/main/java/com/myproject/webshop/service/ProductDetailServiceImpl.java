package com.myproject.webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.webshop.entity.ProductDetail;
import com.myproject.webshop.repository.ProductDetailRepository;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;

	@Override
	public void save(ProductDetail productDetail) {
		productDetailRepository.save(productDetail);
		
	}
}
