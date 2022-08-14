package com.myproject.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.StandingPurchasedItem;

@Repository
public interface StandingProductRepository extends JpaRepository<StandingPurchasedItem, Integer>{

	List<StandingPurchasedItem> findAllByOrderByOrderDateAsc();

}
