package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;

public class TenmoService {
	private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private static String AUTH_TOKEN = "";
    
    public TenmoService(String url) {
    	this.BASE_URL = url;
    }
    
    public BigDecimal getBalance(AuthenticatedUser currentUser) throws AuthenticationServiceException {
    	AUTH_TOKEN = currentUser.getToken();
    	BigDecimal balance = restTemplate.exchange(BASE_URL + "balance/" + currentUser.getUser().getUsername(), HttpMethod.GET, AuthEntity(AUTH_TOKEN), BigDecimal.class).getBody();
    	return balance;
    }
    
    public Transfers addNewTransfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount, String TOKEN) {
    	Transfers transfer = new Transfers(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
    	restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST,  makeTransferEntity(transfer, TOKEN), Transfers.class);
    	return transfer;
    }
    
    public Transfers[] getAllTransactionsByUsers(int user_id, String TOKEN) throws AuthenticationServiceException {
    	Transfers[] transfers = null;
    	transfers = restTemplate.exchange(BASE_URL + "users/transfers/" + user_id, HttpMethod.GET, AuthEntity(TOKEN), Transfers[].class).getBody();
    	return transfers;
    }
    
//    public BigDecimal updateBalance(int accountId, int userId, BigDecimal updatedToBalance) throws AuthenticationServiceException {
//    	Accounts account = new Accounts(accountId, userId, updatedToBalance);
//    	restTemplate.exchange(BASE_URL + "balance/" + account.getUser_id(), HttpMethod.PUT, makeAccountEntity(account), Accounts.class);
//    	return account.getBalance();
//    }
    
//    public Accounts getAccountIdByUserId(int userId) throws AuthenticationServiceException {
//    	return restTemplate.exchange(BASE_URL + "accounts/" + userId, HttpMethod.GET, makeAuthEntity(), Accounts.class).getBody();
//    	
//    }
    
    public User[] getAll() throws AuthenticationServiceException {
    	User[] users = null;
    	users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
    	return users;
    }
    
    public HttpEntity AuthEntity(String token) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(token);
    	HttpEntity entity = new HttpEntity(headers);
    	return entity;
    }
    public HttpEntity makeAuthEntity() {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(AUTH_TOKEN);
    	HttpEntity entity = new HttpEntity(headers);
    	return entity;
    }
    
    public HttpEntity makeTransferEntity(Transfers transfer, String TOKEN) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.setBearerAuth(TOKEN);
    	HttpEntity entity = new HttpEntity(transfer, headers);
    	return entity;
    }
//    public HttpEntity makeAccountEntity(Accounts account) {
//    	HttpHeaders headers = new HttpHeaders();
//    	headers.setContentType(MediaType.APPLICATION_JSON);
//    	headers.setBearerAuth(AUTH_TOKEN);
//    	HttpEntity entity = new HttpEntity(account, headers);
//    	return entity;
//    }
    
   
}
