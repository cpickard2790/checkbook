package com.chad.checkbook.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chad.checkbook.model.Bill;
import com.chad.checkbook.repository.BillRepository;
import com.chad.checkbook.service.BillService;

@RestController
public class BillController {

	@Autowired
	BillRepository billRepository;
	
	@Autowired 
	BillService service;
	
	@GetMapping("/bills")
	public List<Bill> getBills() {
		return billRepository.findAll();
	}

	@GetMapping("/bills/due")
	public List<Bill> getBillsDue() {
		List<Bill> billsDue = service.getBillsDueToday();
		if (billsDue != null || !billsDue.isEmpty()) {
			return billsDue;
		}
		else {
			return Collections.emptyList();
		}
	}

	@GetMapping("/bills/amount")
	public double amountDueToday() {
		return service.getAmountDueToday();
	}
	
	@PostMapping("/bills/add")
	public Bill addBill(@RequestBody Bill bill) {
		return billRepository.save(bill);
	}
	
	@GetMapping("bills/total")
	public double calcTotal() {
		return service.calculateTotalBills();
	}
}
