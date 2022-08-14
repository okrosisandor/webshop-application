package com.myproject.webshop.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="product_rating")
public class ProductRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_rating_id")
	private int id;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name="already_rated")
	private boolean alreadyRated;
	
	@Column(name="rating_date")
	@DateTimeFormat(pattern = "'yyyy.MM.dd HH:mm")
	private Date dateOfRating;
	
	@Column(name="rating")
	private int rating;
	
	@Column(name="opinion")
	private String opinion;

	public ProductRating() {
		super();
	}

	public ProductRating(Customer customer, Product product) {
		super();
		this.customer = customer;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getDateOfRating() {
		return dateOfRating;
	}

	public void setDateOfRating(Date dateOfRating) {
		this.dateOfRating = dateOfRating;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public boolean isAlreadyRated() {
		return alreadyRated;
	}

	public void setAlreadyRated(boolean alreadyRated) {
		this.alreadyRated = alreadyRated;
	}

	@Override
	public String toString() {
		return "ProductRating [id=" + id + ", customer=" + customer.getUserName() + ", product=" + product.getName() + ", dateOfRating="
				+ dateOfRating + ", rating=" + rating + ", opinion=" + opinion + "]";
	}
	
}
