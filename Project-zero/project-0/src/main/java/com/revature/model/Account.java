package com.revature.model;

import java.util.Objects;

public class Account {

	private int id;
	private int balance;
	private String accountType;
	private int clientId;
	
	// Constructors
	public Account() {
	}
	
	public Account(int id, int balance, String accountType) {
		this.id = id;
		this.balance = balance;
		this.accountType = accountType;
	}

	// Getters/setters


	// HashCode, equals, toString
	
	

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
		Account other = (Account) obj;
		return Objects.equals(accountType, other.accountType) && balance == other.balance && clientId == other.clientId
				&& id == other.id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", accountType=" + accountType + ", clientId=" + clientId
				+ "]";
	}

	
	
}