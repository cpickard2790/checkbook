package com.chad.checkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chad.checkbook.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	@Query("SELECT max(id) from Item")
	public Long findLast();
}
