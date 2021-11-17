package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrUpdateAccountDTO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.util.JDBCUtility;

public class AccountDAO {

	public void deleteAllAccountsByClientId(int clientId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM accounts WHERE client_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			
			pstmt.executeUpdate();
		}
	}
	
	public void deleteAccountByClientId(String clientId, int accountId) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "DELETE FROM accounts WHERE account_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, accountId);
			
			pstmt.executeUpdate();
		}
	}
	
	public List<Account> getAllAccountsByClientId(int clientId, int greaterThan, int lessThan) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		
		try (Connection con = JDBCUtility.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance >= ? AND balance <= ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			pstmt.setInt(2, greaterThan);
			pstmt.setInt(3, lessThan);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("account_id");
				int balance = rs.getInt("balance");
				String accountType = rs.getString("account_type");
				
				Account g = new Account(id, balance, accountType);
				
				accounts.add(g);
			}
		}
		
		return accounts;
	}

	public Account updateAccount(int id, int clientId, AddOrUpdateAccountDTO dto) throws SQLException {
		try (Connection con = JDBCUtility.getConnection()) {
			String sql ="update accounts set balance = ?, account_type = ? where client_id =? and account_id = ?";	
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance());
			pstmt.setString(2, dto.getAccountType());
			pstmt.setInt(3, clientId);
			pstmt.setInt(4, id);
			pstmt.executeUpdate();
		}
		return new Account(id, dto.getBalance(), dto.getAccountType());
	}

		//add account
		
		public Account addAccount(String clientId, AddOrUpdateAccountDTO account) throws SQLException {
			
			// try with resources: used when we want for our application to automatically call the .close() method on whatever "resource"
			// we are using
			// The Connection interface defines a close() method. This method, when invoked, will disconnect from the database
			// Whenever we are done with our block of code inside the try-with-resources block, it will automatically do this for us
			// The resource will be closed regardless of whether an exception occurs or the block ends without any exception
			try (Connection con = JDBCUtility.getConnection()) {
				String sql = "INSERT INTO accounts (balance, account_type, client_id) "
						+ " VALUES (?, ?, ?)";
				
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setInt(1, account.getId());
				pstmt.setString(2, account.getAccountType());
				pstmt.setInt(3, account.getClientId());
				
				
				
				
				int numberOfRecordsInserted = pstmt.executeUpdate(); // Instead of executeQuery, like we use for SELECT statements, INSERT, UPDATE, and DELETE will use
				// executeUpdate(). This method returns an integer representing the number of rows that were modified
				
				// If numberOfRecordsInserted is NOT 1, then something went wrong
				if (numberOfRecordsInserted != 1) {
					throw new SQLException("Adding a new Account was unsuccessful");
				}
				
				ResultSet rs = pstmt.getGeneratedKeys();
				
				rs.next(); // iterating to the first "row"
				int automaticallyGeneratedId = rs.getInt(1); // grabbing the first column's information from that "row"
				
				// When we return the Account that was created in the database,
				// The missing data is the automatically generated ID
				// How do we obtain that id?
				return new Account(automaticallyGeneratedId, account.getBalance(),account.getAccountType()); 
			}
		
	}
	
}

	



		

	
