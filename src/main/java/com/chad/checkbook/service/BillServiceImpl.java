package com.chad.checkbook.service;

import java.text.DecimalFormat;
import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chad.checkbook.model.Bill;
import com.chad.checkbook.repository.BillRepository;

import static com.chad.checkbook.service.ServiceHelper.roundTwoDecimals;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	BillRepository repository;

	@Override
	public double calculateTotalBills() {
		double total = 0;
		List<Double> amounts = repository.findAllAmounts();
		for(double amount: amounts) {
			total = total + amount;
		}
		roundTwoDecimals(total);
		return total;
	}
	
	public List<Bill> getBillsDueToday() {
		MonthDay today = MonthDay.now();
		List<Bill> list = repository.findAll();
		List<Bill> billsDueToday = new ArrayList<>();
		for (Bill bill : list) {
			long billDay = bill.getDateDue();
			long todaysDay = today.getLong(ChronoField.DAY_OF_MONTH);
			if ( (todaysDay - billDay) == 0) {
				billsDueToday.add(bill);
			}
		}
		return billsDueToday;
	}

	public double getAmountDueToday() {
		List<Bill> todaysBills = getBillsDueToday();
		double total = 0.0;
		if (todaysBills != null || !todaysBills.isEmpty()) {
			for (Bill bill : todaysBills) {
				total += bill.getAmount();
			}
		}
		return total;
	}
}

