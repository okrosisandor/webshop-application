package com.myproject.webshop.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.myproject.webshop.validation.UniqueEmail;
import com.myproject.webshop.validation.UniqueUsername;

@Entity
@Table(name="customer", uniqueConstraints = @UniqueConstraint(columnNames = "userName"))
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message = "field is required")
	@Size(min = 5, max = 15, message = "length must be between 5 and 15")
	@Column(name="username")
	@UniqueUsername
	private String userName;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="first_name")
	private String firstName;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="last_name")
	private String lastName;
	
	@NotNull(message = "field is required")
	@Size(min = 5, message = "enter a valid email")
	@Column(name="email")
	@UniqueEmail
	@Pattern(regexp="^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "not a valid email")
	private String email;
	
	@NotNull(message = "field is required")
	@Size(min = 6, message = "length must be 6 or more")
	@Column(name="password")
	private String password;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="verification_code", updatable = false)
	private String verificationCode;
	
	@Column(name="date_of_registration")
	private Date dateOfRegistration;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<CustomerCart> customerCart = new ArrayList<CustomerCart>();
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<PurchasedItem> purchasedItems = new ArrayList<PurchasedItem>();
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private List<ProductRating> productRatings = new ArrayList<ProductRating>();
	
	@OneToMany(mappedBy = "customer", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private static List<StandingPurchasedItem> standingPurchasedItems = new ArrayList<StandingPurchasedItem>();
	
	@Valid
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="customer_address_id")
	private CustomerAddress customerAddress;
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
	, fetch=FetchType.EAGER)
	@JoinTable(
		name="user_roles",
		joinColumns = @JoinColumn(name="customer_id"),
		inverseJoinColumns = @JoinColumn(name="role_id"))
	private Collection<Role> roles;

	public Customer() {
		
	}
	
	public Customer(@NotNull @Size(min = 5, message = "length must be 5 or more") String userName,
			@NotNull @Size(min = 1, message = "field must not be empty") String firstName,
			@NotNull @Size(min = 1, message = "field must not be empty") String lastName,
			@NotNull @Size(min = 5, message = "enter a valid email") String email,
			@NotNull @Size(min = 6, message = "length must be 6 or more") String password) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Customer(@NotNull @Size(min = 5, message = "length must be 5 or more") String userName,
			@NotNull @Size(min = 1, message = "field must not be empty") String firstName,
			@NotNull @Size(min = 1, message = "field must not be empty") String lastName,
			@NotNull @Size(min = 5, message = "enter a valid email") String email,
			@NotNull @Size(min = 6, message = "length must be 6 or more") String password,
			Collection<Role> roles) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	public void addRole(String name) {
		Role role = new Role(name);
		this.roles.add(role);
	}

	public static List<StandingPurchasedItem> getStandingPurchasedItems() {
		return standingPurchasedItems;
	}

	public static void setStandingPurchasedItems(List<StandingPurchasedItem> items) {
		Customer.standingPurchasedItems = items;
	}
	
	public void addToStandingPurchasedItems(StandingPurchasedItem item) {
		Customer.standingPurchasedItems.add(item);
	}
	
	public void removeFromStandingPurchasedItems(int id) {
		
		for(int i = 0; i < Customer.standingPurchasedItems.size(); i++) {
			if(standingPurchasedItems.get(i).getProduct().getId() == id) {
				standingPurchasedItems.remove(i);
				break;
			}
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + "]";
	}
	
	public List<CustomerCart> getCustomerCart() {
		return customerCart;
	}

	public void setCustomerCart(List<CustomerCart> customerCart) {
		this.customerCart = customerCart;
	}
	
	public void addToCustomerCart(CustomerCart cart) {
		this.customerCart.add(cart);
	}

	public void removeFromCart(int id) {
		for(int i = 0; i < customerCart.size(); i++) {
			if(customerCart.get(i).getId() == id) {
				customerCart.remove(i);
			}
		}
		
	}

	public List<PurchasedItem> getPurchasedItems() {
		return purchasedItems;
	}

	public void setPurchasedItems(List<PurchasedItem> purchasedItems) {
		this.purchasedItems = purchasedItems;
	}
	
	public void addToPurchasedItems(PurchasedItem purchasedItem) {
		this.purchasedItems.add(purchasedItem);
	}

	public List<ProductRating> getProductRatings() {
		return productRatings;
	}

	public void setProductRatings(List<ProductRating> productRatings) {
		this.productRatings = productRatings;
	}
	
	public void addToProductRatings(ProductRating rating) {
		this.productRatings.add(rating);
	}
	
}
