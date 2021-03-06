package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Accounts {
	private int account_id;
	private int user_id;
	private BigDecimal balance;
	
	public Accounts() {}
	
	public Accounts(int accountId, int userId, BigDecimal balance) {
		this.account_id = accountId;
		this.user_id = userId;
		this.balance = balance;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public BigDecimal setBalance(BigDecimal balance) {
		this.balance = balance;
		return this.balance;
	}
	
	@Override
	public String toString() {
		return "Account ID: " + account_id + "User ID: " + user_id + "Balance is: " + balance; 
	}
}
