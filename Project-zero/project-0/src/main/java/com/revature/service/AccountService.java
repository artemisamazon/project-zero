package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

import io.javalin.http.Context;

public class AccountService {

	private AccountDAO accountDao;
	private ClientDAO clientDao;

	public AccountService() {
		this.accountDao = new AccountDAO();
		this.clientDao = new ClientDAO();
	}

	// For mock objects (Mockito)
	public AccountService(AccountDAO accountDao, ClientDAO clientDao) {
		this.accountDao = accountDao;
		this.clientDao = clientDao;
	}

	// Business logic we want to make sure that this client actually exists
	// If the client does not exist, throw a ClientNotFoundException
	public List<Account> getAllAccountsByClientId(String clientId, Context ctx)
			throws InvalidParameterException, ClientNotFoundException, SQLException {

		List<Account> accounts;

		int id = Integer.parseInt(clientId);

		if (ctx.queryParam("greaterThan") != null && ctx.queryParam("lessThan") != null) { // Using both
			int greaterThan = Integer.parseInt(ctx.queryParam("greaterThan"));
			int lessThan = Integer.parseInt(ctx.queryParam("lessThan"));

			accounts = this.accountDao.getAllAccountsByClientId(id, greaterThan, lessThan);
		} else if (ctx.queryParam("lessThan") != null) { // using only lessThan

			int lessThan = Integer.parseInt(ctx.queryParam("lessThan"));

			accounts = this.accountDao.getAllAccountsByClientId(id, 0, lessThan);
		} else if (ctx.queryParam("greaterThan") != null) { // using only greaterThan

			int greaterThan = Integer.parseInt(ctx.queryParam("greaterThan"));

			accounts = this.accountDao.getAllAccountsByClientId(id, greaterThan, 100);

		} else {
			accounts = this.accountDao.getAllAccountsByClientId(id, 0, 1000000000);
		}

		return accounts;
	}

//	public Account editClientAccount(String accountId, String clientId, int Balance, String accountType)
//			throws SQLException, ClientNotFoundException, InvalidParameterException {
//
//		// Convert the String to an int
//		try {
//			int id = Integer.parseInt(clientId);
//
//			// First, grab the information about the client with a client id of the value
//			// accountId
//			Client clientToEdit = this.clientDao.getClientById(id);
//
//			int accid = Integer.parseInt(accountId);
//
//			// How does getClientById work?
//			// 1st scenario: If we have a accountId that indeed exists in the database, it
//			// will return us a Client object containing the information
//			// 2nd scenario: If we have a accountId that does not have corresponding record
//			// in the database, it will return null
//			// null = absence of an object
//			if (clientToEdit == null) {
//				throw new ClientNotFoundException("Client with an id of " + accountId + " was not found");
//			}
//			
//
//			// This DTO will contain the first name that will be changed, while everything
//			// else stays the same as before
//			AddOrUpdateAccountDTO dto = new AddOrUpdateAccountDTO(id , accountType,clientId);
//			// This DTO object contains the firstName, the lastName, the account type, and
//			// the phone that we want to update the client to
//			// Because we are only updating the firstName, that is the only change inside of
//			// the DTO. Everything else (lastName, account type, and phone) are
//			// populated from the Client object we grabbed from the database (using
//			// getClientById)
//
//			Account updatedAccount = this.accountDao.updateAccount(id, accid, dto);
//
//			return updatedAccount;
//		
//
//		} catch (NumberFormatException e) {
//			throw new InvalidParameterException("Id provided is not an int convertable value");
//
//		}
//	}

	public void deleteAccountByClientId(String clientId, String accountId)
			throws InvalidParameterException, SQLException, ClientNotFoundException {
		try {
			int accid = Integer.parseInt(accountId);
			int id = Integer.parseInt(clientId);

			// Check to see if a client with that id exists or not
			Client client = this.clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException(
						"Client with id " + id + " was not found, and therefore we cannot delete that client");
			}

			this.accountDao.deleteAccountByClientId(clientId, accid);

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int");
		}
	}

	// add account

	public Account addAccount( String clientId,AddOrUpdateAccountDTO dto) throws InvalidParameterException, SQLException {
		
		// dto contains id, balance, account type
		
	
		
		Set<String> validAccountTypes = new HashSet<>();
		validAccountTypes.add("Checking");
		validAccountTypes.add("Savings");
		validAccountTypes.add("Checking Overdraft Protection");
		validAccountTypes.add("Savings Plus");
		validAccountTypes.add("Savings +");
		validAccountTypes.add("Checking Plus");
		validAccountTypes.add("Checking +");
		
		// If validAccountTypes does NOT contain the information provided in the DTO for accountType
		if (!validAccountTypes.contains(dto.getAccountType())) {
			throw new InvalidParameterException("You entered an invalid account type");
		}
		
		if (dto.getBalance() < 0) {
			throw new InvalidParameterException("Balance cannot be less than 0");
		}
		
	
			
		
		
		Account insertedAccount = this.accountDao.addAccount(clientId, dto);
		
		return insertedAccount;
	}
}
