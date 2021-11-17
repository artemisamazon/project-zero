package com.revature.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;

public class ClientService {
	
	private Logger logger = LoggerFactory.getLogger(ClientService.class);
	
	private ClientDAO clientDao; // HAS-A relationship. ClientService HAS-A ClientDAO
	// In other words, ClientService depends on ClientDAO
	// ClientDAO is a dependency of ClientService
	
	// The word dependency has many meanings in programming and technology in general. 
	// 1. Maven dependency: an API of sorts that we are using inside of our own Maven project (Javalin, Postgres Driver, JUnit 5)
	// 2. Object dependencies (ex. a ClientService object depends on a ClientDAO object)
	
	// This constructor up here will create a real ClientDAO object for our ClientService object being constructed
	public ClientService() {
		this.clientDao = new ClientDAO();
	}
	
	// Create a new constructor for us to pass in a mock ClientDAO object
	public ClientService(ClientDAO clientDao) {
		this.clientDao = clientDao; 
	}
	
	/*
	 * This is our service layer logic for updating ONLY the firstName of a client
	 * 
	 * What this method does is first grab the Client with that particular client id from the database
	 * 	- If a client does not exist with that clientId, it will throw a ClientNotFoundException
	 * 	- Otherwise, it will go ahead and call the updateClient method in the DAO layer and provide the appropriate arguments.
	 * 
	 * 
	 */
	public Client editFirstNameOfClient(String clientId, String changedName) throws SQLException, ClientNotFoundException, InvalidParameterException {
		
		// Convert the String to an int
		try {
			int id = Integer.parseInt(clientId);
		
			// First, grab the information about the client with a client id of the value clientId
			Client clientToEdit = this.clientDao.getClientById(id);
		
			// How does getClientById work?
			// 1st scenario: If we have a clientId that indeed exists in the database, it will return us a Client object containing the information
			// 2nd scenario: If we have a clientId that does not have corresponding record in the database, it will return null
			// null = absence of an object
			if (clientToEdit == null) {
				throw new ClientNotFoundException("Client with an id of " + clientId + " was not found");
			}
		
			// This DTO will contain the first name that will be changed, while everything else stays the same as before
			AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO(changedName, clientToEdit.getLastName(), clientToEdit.getMemberType(), clientToEdit.getSSNumber());
			// This DTO object contains the firstName, the lastName, the account type, and the phone that we want to update the client to
			// Because we are only updating the firstName, that is the only change inside of the DTO. Everything else (lastName, account type, and phone) are 
			// populated from the Client object we grabbed from the database (using getClientById)
			
			Client updatedClient = this.clientDao.updateClient(id, dto);
			
			return updatedClient;
		
		} catch(NumberFormatException e) {
			throw new InvalidParameterException("Id provided is not an int convertable value");
		}
	}
	
	// We know based on our logic, if getAllClients from the DAO layer throws a SQLException, our service layer will also throw a SQLException
	// Because we are not catching it in this method
	// We are using throws SQLException in the method signature
	public List<Client> getAllClients() throws SQLException {
		logger.info("getAllClients() invoked");
		
		List<Client> clients = this.clientDao.getAllClients();
		
		return clients;
	}
	
	
	
	public Client getClientById(String clientId) throws SQLException, InvalidParameterException, ClientNotFoundException {
		// convert from a String to an int
		try {
			int id = Integer.parseInt(clientId);
			
			Client s = this.clientDao.getClientById(id);
			
			if (s == null) {
				throw new ClientNotFoundException("Client with id of " + clientId + " was not found");
			}
			
			return s;
		} catch(NumberFormatException e) {
			throw new InvalidParameterException("Id provided is not an int convertable value");
		}
		
	}

	/*
	 * business logic
	 * 1. We don't want firstName to be blank
	 * 2. We don't want lastName to be blank
	 * 3. We want account type to be either Freshman, Sophmore, Junior, or Senior
	 * 4. we don't want phone to be negative
	 */
	public Client addClient(AddOrUpdateClientDTO dto) throws InvalidParameterException, SQLException {
		// dto contains firstName, lastName, account type, phone
		if (dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")) {
			throw new InvalidParameterException("First name and/or last name cannot be blank");
		}
		
		Set<String> validMemberTypes = new HashSet<>();
		validMemberTypes.add("Rewards");
		validMemberTypes.add("rewards");
		validMemberTypes.add("regular");
		validMemberTypes.add("Regular");
		validMemberTypes.add("Rewards Member");
		validMemberTypes.add("rewards member");
		validMemberTypes.add("regular member");
		validMemberTypes.add("Regular Member");
		
		// If validMemberTypes does NOT contain the information provided in the DTO for account type
		if (!validMemberTypes.contains(dto.getMemberType())) {
			throw new InvalidParameterException("You entered an invalid account type");
		}
		
		if (dto.getSSNumber() < 0) {
			throw new InvalidParameterException("SSNumber cannot be less than 0");
		}
		
		// Trim the leading and trailing whitespaces in the first and last names
		dto.setFirstName(dto.getFirstName().trim());
		dto.setLastName(dto.getLastName().trim());
		
		Client insertedClient = this.clientDao.addClient(dto);
		
		return insertedClient;
	}

	/*
	 * 1. Check to see if the clientId provided in the URI is actually an int, and if not, throw an InvalidParameterException
	 * 2. If the client we are trying to delete does not exist, throw a ClientNotFoundException
	 */
	public void deleteClientById(String clientId) throws InvalidParameterException, SQLException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(clientId); 
			
			// Check to see if a client with that id exists or not
			Client client = this.clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException("Client with id " + id + " was not found, and therefore we cannot delete that client");
			}
			
			this.clientDao.deleteClientById(id);
			
		} catch(NumberFormatException e) {
			throw new InvalidParameterException("Id supplied is not an int");
		}
		
	
		}
	}
	
	
