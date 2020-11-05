package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

public interface TransfersDAO {
	public int addTransfer(Integer transferTypeId, Integer transferStatusId, Integer accountFrom, Integer accountTo, Integer amount);
}
