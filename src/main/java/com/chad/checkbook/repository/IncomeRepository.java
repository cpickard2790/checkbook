package com.chad.checkbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chad.checkbook.model.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

}
