package com.myproject.webshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findAllByOrderByNameAsc();
	
	public long countByCategory(String category);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% and p.price >= ?2 and p.price <= ?3")
	public Page<Product> findAllWithPrice(String keyword, double minPrice, double maxPrice, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.price >= ?1 and p.price <= ?2")
	public Page<Product> findAll(double minPrice, double maxPrice, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.price >= ?1 and p.price <= ?2 and p.category = ?3")
	public Page<Product> findAll(double minPrice, double maxPrice, String category, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% and p.price >= ?2 and p.price <= ?3 and p.category = ?4")
	public Page<Product> findAllWithPrice(String keyword, double minPrice, double maxPrice, String category, Pageable pageable);

	@Modifying
	@Query("UPDATE Product p SET p.rating = ?2 WHERE p.id = ?1")
	public void calculateAverage(int id, double rating);

}
