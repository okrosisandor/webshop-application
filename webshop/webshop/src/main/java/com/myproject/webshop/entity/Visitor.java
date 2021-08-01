package com.myproject.webshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visitor")
public class Visitor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="visit_date")
	private Date visitDate;
	
	public Visitor() {
		super();
	}

	public Visitor(String ip, Date visitDate) {
		super();
		this.ipAddress = ip;
		this.visitDate = visitDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@Override
	public String toString() {
		return "Visitor [id=" + id + ", ipAddress=" + ipAddress + ", visitDate=" + visitDate + "]";
	}
	
	
}
