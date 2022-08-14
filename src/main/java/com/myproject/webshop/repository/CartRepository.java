package com.myproject.webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.CustomerCart;

@Repository
public interface CartRepository extends JpaRepository<CustomerCart, Integer>{

}
