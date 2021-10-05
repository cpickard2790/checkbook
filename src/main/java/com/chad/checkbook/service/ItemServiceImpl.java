package com.chad.checkbook.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chad.checkbook.TwilloSender;
import com.chad.checkbook.model.Item;
import com.chad.checkbook.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	ItemRepository repository;
	
	@Override
	public List<Item> getAllItems() {
		return repository.findAll();
	}
	
	@Override
	public Item calculateWithdraw(Item item) {
		Item lastItem = repository.getOne(repository.findLast());
		double total = lastItem.getBalance() - item.getWithdraw();
		item.setBalance(total);
		return repository.save(item);
	}

	@Override
	public Item calculateDeposit(Item item) {
		if (repository.count() != 0) {
			Item lastItem = repository.getOne(repository.findLast());
			double total = lastItem.getBalance() + item.getDeposit();
			item.setBalance(roundTwoDecimals(total));
		}
		else {
			item.setBalance(item.getDeposit());
		}
		return repository.save(item);
	}

	@Override
	public double getCurrentBalance() {
		if (repository.count() > 0) {
			Item item = repository.getOne(repository.findLast());
			return item.getBalance();
		}
		return 0.0;
	}

	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}


}
