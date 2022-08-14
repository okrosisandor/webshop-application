package com.myproject.webshop.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.myproject.webshop.entity.Customer;
import com.myproject.webshop.entity.CustomerCart;
import com.myproject.webshop.entity.PurchasedItem;
import com.myproject.webshop.entity.StandingPurchasedItem;
import com.myproject.webshop.entity.Visitor;

@Service
public interface CustomerService extends UserDetailsService{
	
	public Customer save(Customer customer);

	public List<Customer> findAll();
	
	public List<String> getUsernames();

	public Customer findById(int id);

	public void sendVerificationEmail(@Valid Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException;

	public boolean verify(String code);

	public void updateForgottenPassword(Customer customer);

	public Customer findByEmail(String email);

	public void sendForgottenPasswordEmail(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException;

	public List<String> getEmails();

	public Customer findByUsername(String username);

	public void addToCart(CustomerCart cart);

	public void removeFromCart(int cartId);

	public CustomerCart getCart(int cartId);

	public void addToPurchasedItems(PurchasedItem item);

	void updateCustomer(Customer customer);

	public void addToStandingPurchasedItems(StandingPurchasedItem standingItem);

	public List<PurchasedItem> getPurchasedItems();

	public List<Customer> todayRegistrations();

	public List<Customer> thisMonthsRegistrations();

	public void saveVisit(Visitor visitor);

	public List<Visitor> todaysVisitors();

	public List<Visitor> thisMonthsVisitors();

	public List<Visitor> totalVisitors();

}
