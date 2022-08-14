package com.myproject.webshop.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.webshop.entity.Customer;
import com.myproject.webshop.entity.CustomerCart;
import com.myproject.webshop.entity.PurchasedItem;
import com.myproject.webshop.entity.Role;
import com.myproject.webshop.entity.StandingPurchasedItem;
import com.myproject.webshop.entity.Visitor;
import com.myproject.webshop.repository.CartRepository;
import com.myproject.webshop.repository.CustomerRepository;
import com.myproject.webshop.repository.PurchasedRepository;
import com.myproject.webshop.repository.StandingProductRepository;
import com.myproject.webshop.repository.VisitorRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private VisitorRepository visitorRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private PurchasedRepository purchasedRepository;
	
	@Autowired
	private StandingProductRepository standingProductRepository;

	@Override
	public Customer save(Customer customer) {
		customer.setRoles(Arrays.asList(new Role("ROLE_USER")));
		customer.setEnabled(false);
		
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		
		customer.setVerificationCode(RandomString.make(64));
		
		customer.setDateOfRegistration(new Date());
		
		customerRepository.save(customer);
		
		return customer;
		
	}
	
	@Override
	public void updateCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		
		customerRepository.save(customer);
	}

	public void sendVerificationEmail(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException{

		String subject = "Please verify your registration";
		String senderName = "MyShop Team";
		String mailContent = "<p>Dear " + customer.getFirstName() + " " + customer.getLastName() + ",</p>";
		mailContent += "<p>Please click on the link to verify your registration:</p>";
		
		String verifyURL = siteURL + "/customers/verify?code=" + customer.getVerificationCode();
		
		mailContent += "<h3><a= href=\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p>Thank you<br>The MyShop Team</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("myshopapp123@gmail.com", senderName);
		helper.setTo(customer.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		
		mailSender.send(message);
	}
	
	@Transactional
	public boolean verify(String verificationCode) {
		Customer customer = customerRepository.findByVerificationCode(verificationCode);
		
		if(customer == null || customer.isEnabled()) {
			return false;
		}else {
			customerRepository.enable(customer.getId());
			return true;
		}
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Customer findById(int id) {
		Optional<Customer> result = customerRepository.findById(id);
		
		Customer customer = null;
		
		if(result.isPresent()) {
			customer = result.get();
		}else {
			throw new RuntimeException("Did not find customer id - " + id);
		}
		
		return customer;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		Customer customer = customerRepository.findByUserName(username);
		
		if(customer == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		//Spring Security által létrehozott User Class, ami kiterjeszti az UserDetails interface-t, ezért vissza tudjuk adni
		//A paraméterei a belépési adatok, amiket a loginnál megadunk, a 3. pedig a role(ok)
		return new User(customer.getUserName(), customer.getPassword(), mapRolesToAuthorities(customer.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
	}

	@Override
	public List<String> getUsernames() {
		return customerRepository.getUsernames();
	}
	
	@Override
	public List<String> getEmails() {
		return customerRepository.getEmails();
	}

	@Override
	@Transactional
	public void updateForgottenPassword(Customer customer) {
		
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		
	}

	@Override
	public Customer findByEmail(String email) {
		
		Customer customer = customerRepository.findByEmail(email);
		
		return customer;
	}
	
	@Override
	public Customer findByUsername(String username) {
		
		Customer customer = customerRepository.findByUserName(username);
		
		return customer;
	}

	@Override
	public void sendForgottenPasswordEmail(Customer customer, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String subject = "Forgotten Password";
		String senderName = "MyShop Team";
		String mailContent = "<p>Dear " + customer.getFirstName() + " " + customer.getLastName() + ",</p>";
		mailContent += "<p>A request was made with your email for a password change. To restore your password click the link below:</p>";
		
		String verifyURL = siteURL + "/customers/getPassword?id=" + customer.getId();
		
		mailContent += "<h3><a= href=\"" + verifyURL + "\">Restore</a></h3>";
		mailContent += "<p>Thank you<br>The MyShop Team</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("cbrec123@gmail.com", senderName);
		helper.setTo(customer.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		
		mailSender.send(message);
		
	}


	@Override
	public void addToCart(CustomerCart cart) {
		cartRepository.save(cart);
		
	}

	@Override
	public void removeFromCart(int cartId) {
		cartRepository.deleteById(cartId);
		
	}

	@Override
	public CustomerCart getCart(int cartId) {
		Optional<CustomerCart> result = cartRepository.findById(cartId);
		
		CustomerCart cart = null;
		
		if(result.isPresent()) {
			cart = result.get();
		}else {
			throw new RuntimeException("Did not find cart id - " + cartId);
		}
		
		return cart;
		
	}


	@Override
	public void addToPurchasedItems(PurchasedItem item) {
		purchasedRepository.save(item);
		
	}

	@Override
	public void addToStandingPurchasedItems(StandingPurchasedItem standingItem) {
		standingProductRepository.save(standingItem);
	}

	@Override
	public List<PurchasedItem> getPurchasedItems() {
		return purchasedRepository.findAllByOrderByOrderDateAsc();
	}

	@Override
	public List<Customer> todayRegistrations() {
		return customerRepository.todayRegistrations();
	}

	@Override
	public List<Customer> thisMonthsRegistrations() {
		return customerRepository.thisMonthsRegistrations();
	}

	@Override
	public void saveVisit(Visitor visitor) {
		visitorRepository.save(visitor);
	}

	@Override
	public List<Visitor> todaysVisitors() {
		return visitorRepository.todaysVisitors();
	}

	@Override
	public List<Visitor> thisMonthsVisitors() {
		return visitorRepository.thisMonthsVisitors();
	}

	@Override
	public List<Visitor> totalVisitors() {
		return visitorRepository.findAll();
	}

}
