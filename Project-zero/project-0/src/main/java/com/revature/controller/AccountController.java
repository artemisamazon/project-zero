package com.revature.controller;



import java.util.List;

import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController {

	private AccountService accountService;

	public AccountController() {
		this.accountService = new AccountService();
	}

	private Handler getAllAccountsByClientId = (ctx) -> {
		String clientId = ctx.pathParam("id");

		// Using query parameters, you can have 4 situations:
		// 1. No lessThan or greaterThan at all
		// 2. Using ONLY lessThan
		// 3. Using ONLY greaterThan
		// 4. Using BOTH
		
		// We are dealing with these 4 situations in our service layer by passing in the ctx object, so that our service layer
		// can examine the query parameters
		List<Account> accounts = this.accountService.getAllAccountsByClientId(clientId, ctx);

		ctx.json(accounts);
	};
	
//	private Handler editClientAccount = (ctx) -> {
//		
//		// Extract the client's id from the URI path
//		String clientId = ctx.pathParam("id");
//		String accountId = ctx.pathParam("accid");
//				
//		// Extracts the information in the form of JSON from the body and place it into an object of the type EditFirstNameDTO
//		AddOrUpdateAccountDTO dto = ctx.bodyAsClass(AddOrUpdateAccountDTO.class);
//		
//		// When we invoke the editFirstNameOfClient method (service layer), it will then contact the updateClient method in the DAO layer, which
//		// will then return the Client object representation of the record that was newly updated
//		Account accountThatWasJustEdited = this.accountService.editClientAccount( accountId, clientId, dto.getBalance(), dto.getAccountType());
//		
//		// We then take that object and convert it into JSON
//		// This JSON is then sent back to the client that sent the request (in our case, Postman)
//		ctx.json(accountThatWasJustEdited); // For this to work, we need to have getter methods that exist for that object
//		
//	};
	
	private Handler deleteAccountByClientId = (ctx) -> {
		String accid = ctx.pathParam("accid");
		String id = ctx.pathParam("id");
		
		this.accountService.deleteAccountByClientId(accid, id);
	};
	
	//add account
	private Handler addAccount = (ctx) -> {
		AddOrUpdateAccountDTO dto = ctx.bodyAsClass(AddOrUpdateAccountDTO.class);
		String clientId = ctx.pathParam("clientId");
		Account a = this.accountService.addAccount(clientId, dto);
		
		ctx.json(a);
		ctx.status(201); // 201 CREATED
	};
	
	
	

	public void registerEndpoints(Javalin app) {
		app.get("/clients/{id}/accounts", getAllAccountsByClientId);
		//app.put("/clients/{id}/accounts/{accid}", editClientAccount);
		app.delete("/clients/{id}/accounts/{accid}", deleteAccountByClientId);
		app.post("/clients/{id}/accounts", addAccount);
	
	}

}
