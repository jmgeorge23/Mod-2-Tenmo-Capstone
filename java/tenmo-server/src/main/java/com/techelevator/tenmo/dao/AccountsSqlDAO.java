package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.model.Accounts;

public class AccountsSqlDAO implements AccountsDAO {
	private JdbcTemplate jdbcTemplate;

    public AccountsSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	 @Override
	    public double updateBalance(double newBalance, int userId) {
	    	return jdbcTemplate.update("UPDATE accounts SET balance = ? WHERE user_id = ?;", double.class, newBalance, userId);
	    }
	 @Override
	 public int getAccountIdByUserId(int userId) {
		 return jdbcTemplate.queryForObject("SELECT account_id FROM accounts WHERE user_id = ?;", Integer.class,userId);
	 }
}
