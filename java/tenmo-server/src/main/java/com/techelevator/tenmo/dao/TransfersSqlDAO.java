package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.tenmo.model.Transfers;

public class TransfersSqlDAO implements TransfersDAO{
	private JdbcTemplate jdbcTemplate;

    public TransfersSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	@Override
	    public int addTransfer(Integer transferTypeId, Integer transferStatusId, Integer accountFrom, Integer accountTo, Integer amount) {
	    	int result = jdbcTemplate.update("INSERT into transfers(transfer_type_id, transfer_status_id, account_from, account_to, amount)"
	    			+ "VALUES (?,?,?,?,?);", String.class, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
	    	return result;
	    }

}
