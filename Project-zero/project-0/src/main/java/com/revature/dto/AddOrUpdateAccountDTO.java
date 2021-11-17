package com.revature.dto;

import java.util.Objects;

/*
 * DTO (Data Transfer Object): An object that contains properties, getters/setters and possibly an overridden equals + hashCode and toString method
 * That is used to encapsulate data and pass that data around as this single object
 * 	This data will be stored as instance variables/properties of that object.
 * 
 * Model - Is a type of DTO, but more specifically, it has all of the properties associated with the database representation
 * 	of a row in a table
 * 
 * For example, the Account class is a model of rows in the clients table
 * 
 */

// This is a DTO used to, in our case, add or update a client in the clients table
public class AddOrUpdateAccountDTO {

	// This DTO does not have an ID, because to Add a new client or to update a
	// previous already existing client,
	// we just need the core properties

	private int id;
	private int balance;
	private String accountType;
	private int clientId;

	public AddOrUpdateAccountDTO() {
	}

	public AddOrUpdateAccountDTO(int id, int balance, String accountType, int clientId) {
		this.id = id;
		this.balance = balance;
		this.accountType = accountType;
		this.clientId = clientId;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "AddOrUpdateAccountDTO [id=" + id + ", balance=" + balance + ", accountType=" + accountType
				+ ", clientId=" + clientId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountType, balance, clientId, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateAccountDTO other = (AddOrUpdateAccountDTO) obj;
		return Objects.equals(accountType, other.accountType) && balance == other.balance && clientId == other.clientId
				&& id == other.id;
	}

}
