package com.chad.checkbook.service;

import com.chad.checkbook.model.Bill;

import java.util.List;

public interface BillService {
	double calculateTotalBills();
	List<Bill> getBillsDueToday();
	double getAmountDueToday();
}
