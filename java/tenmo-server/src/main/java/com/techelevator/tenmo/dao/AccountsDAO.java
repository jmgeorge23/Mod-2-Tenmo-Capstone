package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDAO {
	
	//void updateBalance(BigDecimal balance, int userId);
	
	public List<Accounts> findAllAccounts();

	void updateBalance(BigDecimal currentbalance, BigDecimal amount, int userId);
}
