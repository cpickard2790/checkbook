package com.chad.checkbook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String date;
	private String description;
	private String type;
	private double withdraw;
	private double deposit;
	private double balance;
	
	public Item() {}
	
	// Start balance
	public Item(String date, String description, double balance) {
		this.date = date;
		this.description = description;
		this.balance = balance;
	}
	
	public Item(String date, String description, String type, double withdraw, double deposit) {
		System.out.println("From constructor");
		this.date = date;
		this.description = description;
		this.type = type;
		if(type.equals("deposit")) {
			this.deposit = deposit;
		}
		else if(type.equals("withdraw")) {
			this.withdraw = withdraw;
		}
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getWithdraw() {
		return withdraw;
	}
	
	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}
	
	public double getDeposit() {
		return deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
