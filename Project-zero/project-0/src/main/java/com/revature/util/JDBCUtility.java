package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.Driver;

public class JDBCUtility {

	// Recap: declaring an exception is where you use throws <exception name> in the method signature
	// It allows you to pass the responsibility of handling the checked exception to a method that invokes this particular method
	
	public static Connection getConnection() throws SQLException {
		// Whenever we use JDBC, we always need a connection object. This forms the
		// basis of our actual connection
		// to the database that allows us to do what we want on that particular database
		
		// If we need to use this code over and over and over again, it would be good to compartmentalize this into a utility class
		// Utility class = A class that contains static members
		// (ex. Collections class from Collections API that contains useful static methods to work with various collections)

		String url = "jdbc:postgresql://localhost:5432/postgres";
		String username = "postgres";
		String password = "pswd";

		Driver postgresDriver = new Driver();		
		DriverManager.registerDriver(postgresDriver);

		Connection con = DriverManager.getConnection(url, username, password);
		
		return con;
	}
	
}