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
    
    public Transfers addTransfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal amount, String TOKEN) {
    	Transfers transfer = new Transfers(transferTypeId, transferStatusId, accountFrom, accountTo, amount);
    	restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST,  makeTransferEntity(transfer, TOKEN), Transfers.class);
    	return transfer;
    }
    
    public Transfers[] getAllTransactionsByUsers(int user_id, String TOKEN) throws AuthenticationServiceException {
    	Transfers[] transfers = null;
    	transfers = restTemplate.exchange(BASE_URL + "users/transfers/" + user_id, HttpMethod.GET, AuthEntity(TOKEN), Transfers[].class).getBody();
    	return transfers;
    }
    
    public Transfers getTransferByTransferId(int transfer_id, String token) {
    	Transfers transfers = restTemplate.exchange(BASE_URL + "transfers/" + transfer_id , HttpMethod.GET, AuthEntity(token), Transfers.class).getBody();
    	return transfers;
    }

    public User[] getAll(String token) throws AuthenticationServiceException {
    	User[] users = null;
    	users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, AuthEntity(token), User[].class).getBody();
    	return users;
    }
    
    public HttpEntity AuthEntity(String token) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setBearerAuth(token);
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

    
   
}
