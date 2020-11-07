package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

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
	 
	 @ResponseStatus(HttpStatus.FOUND)
	 @RequestMapping(value = "/balance/{username}", method = RequestMethod.GET)
	    public BigDecimal getBalance(@Valid @PathVariable String username) {
	    	return userDAO.getBalanceByUser(username);
	    }
	    
	 	@ResponseStatus(HttpStatus.FOUND)
	    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
	    public List<Accounts> getAccounts() {
	    	return accountsDAO.findAllAccounts();
	    }
	    
	 	@ResponseStatus(HttpStatus.FOUND)
	    @RequestMapping(value = "/users", method = RequestMethod.GET)
	    public List<User> list() {
	    	return userDAO.findAll();
	    }
	    
	 	@ResponseStatus(HttpStatus.FOUND)
	    @RequestMapping(value = "/users/transfers/{user_id}", method = RequestMethod.GET)
	    public List<Transfers> listTransfers(@PathVariable int user_id) {
	    	return transfersDAO.findAllTransfers(user_id);
	    }
	    
	    @ResponseStatus(HttpStatus.CREATED)
	    @RequestMapping(value = "/transfers", method = RequestMethod.POST)
	    	public void addTransfer(@RequestBody Transfers transfers) {
	    	
	    		transfersDAO.addTransfer(transfers);
	    		for(User user: userDAO.findAll()) {
	    			if(user.getId() == transfers.getAccount_from() && transfers.getTransfer_status_id() != 3) {
	    				accountsDAO.updateBalance(userDAO.getBalanceByUser(user.getUsername()).subtract(transfers.getAmount()), transfers.getAccount_from());
	    			}else if(user.getId() == transfers.getAccount_to()&& transfers.getTransfer_status_id() != 3) {
	    				accountsDAO.updateBalance(userDAO.getBalanceByUser(user.getUsername()).add(transfers.getAmount()), transfers.getAccount_to());
	    				
	    			}
	    		}
	    		
	    	}
	    
	    @ResponseStatus(HttpStatus.FOUND)
	    @RequestMapping(value = "/transfers/{transferId}", method = RequestMethod.GET)
	    public Transfers findTransferByTransferId(@PathVariable int transferId) {
	    	return transfersDAO.findTransferByTransferId(transferId);
	    }
}
