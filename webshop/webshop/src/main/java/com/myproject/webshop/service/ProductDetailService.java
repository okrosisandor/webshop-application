package com.myproject.webshop.service;

import org.springframework.stereotype.Service;

import com.myproject.webshop.entity.CustomerAddress;
import com.myproject.webshop.entity.ProductDetail;

@Service
public interface ProductDetailService {

	public void save(ProductDetail productDetail);
}
