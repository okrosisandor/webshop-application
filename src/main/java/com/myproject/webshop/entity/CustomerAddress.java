package com.myproject.webshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="customer_address")
public class CustomerAddress {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="country")
	private String country;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="city")
	private String city;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="zip_code")
	private String zipCode;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="address")
	private String address;
	
	@NotNull(message = "field is required")
	@Size(min = 1, message = "field must not be empty")
	@Column(name="phone")
	private String phone;
	
	public CustomerAddress() {
		super();
	}
	
	public CustomerAddress(
			@NotNull(message = "field is required") @Size(min = 1, message = "field must not be empty") String country,
			@NotNull(message = "field is required") @Size(min = 1, message = "field must not be empty") String city,
			@NotNull(message = "field is required") @Size(min = 1, message = "field must not be empty") String zipCode,
			@NotNull(message = "field is required") @Size(min = 1, message = "field must not be empty") String address,
			@NotNull(message = "field is required") @Size(min = 1, message = "field must not be empty") String phone) {
		super();
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.address = address;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "CustomerAddress [id=" + id + ", country=" + country + ", city=" + city + ", zipCode=" + zipCode
				+ ", address=" + address + ", phone=" + phone + "]";
	}

}
