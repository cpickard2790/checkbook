package com.chad.checkbook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	private String name;
	private long dayDue;
	private double amount;
	
	public Bill() {}
	
	public Bill(String name, long dayDue, double amount) {
		this.name = name;
		this.dayDue = dayDue;
		this.amount = amount;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getDateDue() {
		return dayDue;
	}
	
	public void setDateDue(long dayDue) {
		this.dayDue = dayDue;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
