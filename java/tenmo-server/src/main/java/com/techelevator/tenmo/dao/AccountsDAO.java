package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;

public interface AccountsDAO {
	
	double updateBalance(double balance, int userId);
	
	int getAccountIdByUserId(int userId);
}
