package com.myproject.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.PurchasedItem;

@Repository
public interface PurchasedRepository extends JpaRepository<PurchasedItem, Integer>{

	List<PurchasedItem> findAllByOrderByOrderDateAsc();

	@Query("SELECT p FROM PurchasedItem p WHERE DATE(p.orderDate) = date_format(current_timestamp(), '%Y-%m-%d')")
	List<PurchasedItem> todaysOrders();

	@Query("SELECT p FROM PurchasedItem p WHERE DATE(p.orderDate) >= date_format(current_timestamp(), '%Y-%m-01')")
	List<PurchasedItem> thisMonthsOrders();

}
