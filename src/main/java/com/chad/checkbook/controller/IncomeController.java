package com.chad.checkbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chad.checkbook.model.Income;
import com.chad.checkbook.repository.IncomeRepository;

@RestController
public class IncomeController {

	@Autowired
	IncomeRepository repository;
	
	@GetMapping("/income")
	public List<Income> getIncome() {
		return repository.findAll();
	}
	
	@PostMapping("/income")
	public Income addIncome(@RequestBody Income income) {
		return repository.save(income);
	}
}
