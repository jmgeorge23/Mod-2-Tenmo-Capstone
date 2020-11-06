package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfers;

public interface TransfersDAO {
	public void addTransfer(int transferId, int transferTypeId, Integer transferStatusId, Integer accountFrom, Integer accountTo, BigDecimal amount);
	public List<Transfers> findAllTransfers();
	public Transfers findTransferByTransferId(int transferId);
}
