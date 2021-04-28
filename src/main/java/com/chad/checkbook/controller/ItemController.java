package com.chad.checkbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chad.checkbook.TwilloSender;
import com.chad.checkbook.model.Item;
import com.chad.checkbook.repository.ItemRepository;
import com.chad.checkbook.service.ItemService;

@CrossOrigin(origins = "*")
@RestController
public class ItemController {

	@Autowired
	ItemRepository repository;
	
	@Autowired
	ItemService service;
	
	@GetMapping("/items")
	public List<Item> getItems() {
		return repository.findAll();
	}
	
	@GetMapping("/items/currentBalance")
	public double getBalance() {
		return service.getCurrentBalance();
	}
	
	@PostMapping("/items/initialItem")
	public Item addFirstItem(@RequestBody Item item) {
		return repository.save(item);
	}
	
	@PostMapping("/items/withdraw")
	public Item withdrawItem(@RequestBody Item item) {
		return service.calculateWithdraw(item);
		
	}
	
	@PostMapping("/items/deposit")
	public Item depositItem(@RequestBody Item item) {
		service.calculateDeposit(item);
		return item;
	}
}
