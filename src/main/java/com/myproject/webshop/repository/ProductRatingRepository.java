package com.myproject.webshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.ProductRating;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer>{

	Page<ProductRating> findAllByOrderByDateOfRatingDesc(Pageable pageable);

	@Query("SELECT r FROM ProductRating r WHERE r.product.id = ?1 order by r.dateOfRating desc")
	Page<ProductRating> findAllByOrderByDateOfRatingDesc(int id, Pageable pageable);

}
