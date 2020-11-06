package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.User;

@Service
public class AccountsSqlDAO implements AccountsDAO {
	private JdbcTemplate jdbcTemplate;

    public AccountsSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	 @Override
	    public void updateBalance(BigDecimal newBalance, int userId) {
	    	jdbcTemplate.update("UPDATE accounts SET balance = ? WHERE user_id = ?", newBalance, userId);
	    	
	    }
	    @Override
	    public List<Accounts> findAllAccounts() {
	        List<Accounts> accounts = new ArrayList<>();
	        String sql = "select * from accounts";

	        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
	        while(results.next()) {
	            Accounts account = mapRowToAccount(results);
	            accounts.add(account);
	        }

	        return accounts;
	    }
	    
	    private Accounts mapRowToAccount(SqlRowSet rs) {
	        Accounts account = new Accounts();
	        account.setAccount_id(rs.getInt("account_id"));
	        account.setUser_id(rs.getInt("user_id"));
	        account.setBalance(rs.getBigDecimal("balance"));

	        return account;
	    }
}
