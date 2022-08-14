package com.myproject.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer>{

	@Query("SELECT v FROM Visitor v WHERE DATE(v.visitDate) = date_format(current_timestamp(), '%Y-%m-%d')")
	List<Visitor> todaysVisitors();

	@Query("SELECT v FROM Visitor v WHERE DATE(v.visitDate) >= date_format(current_timestamp(), '%Y-%m-01')")
	List<Visitor> thisMonthsVisitors();
}
