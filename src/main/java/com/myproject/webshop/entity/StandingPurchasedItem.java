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
@Table(name="standing_purchased_item")
public class StandingPurchasedItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purchased_item_id")
	private int id;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name="order_date")
	@DateTimeFormat(pattern = "'yyyy.MM.dd HH:mm")
	private Date orderDate;
	
	@Column(name="already_paid")
	private boolean alreadyPaid;
	
	@Column(name="quantity")
	private int quantity;

	public StandingPurchasedItem() {
		super();
	}

	public StandingPurchasedItem(Customer customer, Product product) {
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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public boolean isAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(boolean alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "StandingPurchasedItem [id=" + id + ", customer=" + customer.getId() + ", product=" + product.getId() + ", orderDate="
				+ orderDate + ", alreadyPaid=" + alreadyPaid + ", quantity=" + quantity + "]";
	}
	
}
