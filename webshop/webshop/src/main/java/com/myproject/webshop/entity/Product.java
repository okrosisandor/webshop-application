package com.myproject.webshop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="price")
	private double price;
	
	@Column(name="category")
	private String category;
	
	@Lob
	@Column(name="image")
	private String image;
	
	@Column(name="rating")
	private double rating;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="product_details_id")
	private ProductDetail productDetail;
	
	@OneToMany(mappedBy = "product")
	private List<CustomerCart> customerCart = new ArrayList<CustomerCart>();
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductRating> productRatings = new ArrayList<ProductRating>();
	
	public Product() {
		super();
	}

	public Product(String name, double price, String category, String image) {
		super();
		this.name = name;
		this.price = price;
		this.category = category;
		this.image = image;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + "]";
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
