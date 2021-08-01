package com.myproject.webshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myproject.webshop.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findAllByOrderByLastNameAsc();
	
	Customer findByUserName(String username);

	@Query("SELECT c.userName FROM Customer c")
	List<String> getUsernames();
	
	@Query("SELECT c.email FROM Customer c")
	List<String> getEmails();
	
	@Modifying
	@Query("UPDATE Customer c SET c.enabled = true WHERE c.id = ?1")
	public void enable(Integer id);
	
	public Customer findByVerificationCode(String verificationCode);

	public Customer findByEmail(String email);
	
	@Query("SELECT c FROM Customer c WHERE DATE(c.dateOfRegistration) >= date_format(current_timestamp(), '%Y-%m-01')")
	public List<Customer> thisMonthsRegistrations();
	
	@Query("SELECT c FROM Customer c WHERE DATE(c.dateOfRegistration) = date_format(current_timestamp(), '%Y-%m-%d')")
	public List<Customer> todayRegistrations();

}
