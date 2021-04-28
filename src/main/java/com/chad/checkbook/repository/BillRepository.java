package com.chad.checkbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chad.checkbook.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query("SELECT amount from Bill")
	public List<Double> findAllAmounts();
}
