package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class TenmoController {
	 private UserDAO userDAO;
	 private AccountsDAO accountsDAO;
	 private TransfersDAO transfersDAO;
	 
	 public TenmoController(UserDAO userDAO, AccountsDAO accountsDAO, TransfersDAO transfersDAO ) {
		 this.userDAO = userDAO;
	     this.accountsDAO = accountsDAO;
	     this.transfersDAO = transfersDAO;
	 }
	 
	 @RequestMapping(value = "/balance/{username}", method = RequestMethod.GET)
	    public BigDecimal getBalance(@PathVariable String username) {
	    	return userDAO.getBalanceByUser(username);
	    }
	    
//	    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.PUT)
//	    public void updateBalance(@RequestBody Accounts account, @PathVariable int userId) {
//	    	accountsDAO.updateBalance(transfersDAO.findTransferByTransferId(transfers.) account.getBalance(), account.getUser_id());
//	    }
	    
	    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
	    public List<Accounts> getAccounts() {
	    	return accountsDAO.findAllAccounts();
	    }
	    
	    @RequestMapping(value = "/users", method = RequestMethod.GET)
	    public List<User> list() {
	    	return userDAO.findAll();
	    }
	    
	    @RequestMapping(value = "/users/transfers/{user_id}", method = RequestMethod.GET)
	    public List<Transfers> listTransfers(@PathVariable int user_id) {
	    	return transfersDAO.findAllTransfers(user_id);
	    }
	    
	    @ResponseStatus(HttpStatus.CREATED)
	    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
	    	public void addTransfer(@RequestBody Transfers transfers) {
	    		//int transferId = transfers.getMaxIdPlusOne();
	    	//transferId,transfers.getTransfer_type_id(), transfers.getTransfer_status_id(), transfers.getAccount_from(), transfers.getAccount_to(), transfers.getAmount()
	    		transfersDAO.addTransfer(transfers);
	    		for(User user: userDAO.findAll()) {
	    			if(user.getId() == transfers.getAccount_from()) {
	    				accountsDAO.updateBalance(userDAO.getBalanceByUser(user.getUsername()).subtract(transfers.getAmount()), transfers.getAccount_from());
	    			}else if(user.getId() == transfers.getAccount_to()) {
	    				accountsDAO.updateBalance(userDAO.getBalanceByUser(user.getUsername()).add(transfers.getAmount()), transfers.getAccount_to());
	    				
	    			}
	    		}
	    		
	    	}
	    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
	    public Transfers findTransferByTransferId(@RequestBody Transfers transfer, @PathVariable int transferId) {
	    	return transfersDAO.findTransferByTransferId(transferId);
	    }
}
