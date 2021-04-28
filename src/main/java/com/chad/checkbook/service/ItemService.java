package com.chad.checkbook.service;

import java.util.List;

import com.chad.checkbook.model.Item;

public interface ItemService {

	List<Item> getAllItems();
	Item calculateWithdraw(Item item);
	Item calculateDeposit(Item item);
	double getCurrentBalance();
}
