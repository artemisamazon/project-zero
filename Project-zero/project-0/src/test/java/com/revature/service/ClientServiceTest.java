package com.revature.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;

public class ClientServiceTest {

	// Define the System under test (SUT): ClientService
	private ClientService sut;

	
	/*
	 * ClientService's getAllClients() tests
	 */
	
	// Positive test (happy path)
	@Test
	public void testGetAllClientsPositive() throws SQLException {
		
		/*
		 * AAA
		 * 
		 * A - Arrange
		 * A - Act
		 * A - Assert
		 */
		
		
		/*
		 * ARRANGE
		 */
		
		// ClientService requires a ClientDAO object to function properly
		// So, let's go ahead and mock a ClientDAO object
		ClientDAO mockClientDao = mock(ClientDAO.class); // this is a fake object, because we're not using "new <constructor>())", we are using mock from Mockito
		// That creates a fake object that we can specify scenarios for, whenever we call that object's instance methods
		
		Client client1 = new Client(10, "Sarah", "Jones", "Regular", 222559999);
		Client client2 = new Client(100, "Karma", "Smith", "Rewards", 22188888);
		Client client3 = new Client(15, "Patty", "Samuels", "Regular", 22665555);
		
		List<Client> clientsFromDao = new ArrayList<>();
		clientsFromDao.add(client1);
		clientsFromDao.add(client2);
		clientsFromDao.add(client3);
		
		when(mockClientDao.getAllClients()).thenReturn(clientsFromDao);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT
		 */
		List<Client> actual = clientService.getAllClients();
		
		/*
		 * ASSERT
		 */
		List<Client> expected = new ArrayList<>();
		expected.add(new Client(10, "Sarah", "Jones", "Regular", 222559999));
		expected.add(new Client(100, "Karma", "Smith", "Rewards", 22188888));
		expected.add(new Client(15, "Patty", "Samuels", "Regular", 22665555));
		
		Assertions.assertEquals(expected, actual);
		
	}
	
	// Negative Test
	@Test
	public void testGetAllClientsSQLExceptionOccursNegative() throws SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		when(mockClientDao.getAllClients()).thenThrow(SQLException.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT AND ASSERT
		 */
		
		// Our test will pass, if the code inside the second argument's lambda expression throws a SQLException
		// If no exception occurs, then the test will fail
		Assertions.assertThrows(SQLException.class, () -> {
			
			clientService.getAllClients(); // we actually want for SQLException to emanate from our clientService getAllClients
			
		});
		
	}
	
	/*
	 * ClientService's getClientById(int id)
	 */
	
	// Positive test (happy path)
	@Test
	public void testGetClientByIdPositive() throws SQLException, InvalidParameterException, ClientNotFoundException {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		when(mockClientDao.getClientById(eq(5))).thenReturn(new Client(5, "Kenny", "Lewis", "Rewards", 22777999));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT
		 */
		Client actual = clientService.getClientById("5");
		
		/*
		 * ASSERT
		 */
		
		Assertions.assertEquals(new Client(5, "Kenny", "Lewis", "Rewards", 22777999), actual);
	}
	
	// Negative Test
	// ClientNotFoundException is thrown
	@Test
	public void testGetClientByIdNotFoundNegative() throws SQLException, InvalidParameterException, ClientNotFoundException {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		// By default, any object returned from one of the methods called from the mock client dao will return null
		// So here we are not specifying any when(...).thenReturn(...);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT AND ASSERT
		 */
		
		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			clientService.getClientById("1"); // ACT
		});
		
		
	}
	
	// Negative Test
	// InvalidParameterException is thrown
	@Test
	public void testGetClientByIdAlphabeticalIdNegative() {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		// By default, any object returned from one of the methods called from the mock client dao will return null
		// So here we are not specifying any when(...).thenReturn(...);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT AND ASSERT
		 */
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			clientService.getClientById("abc"); // ACT
		});
	}
	
	// Negative Test
	// InvalidParameterException is thrown
	@Test
	public void testGetClientByIdDecimalIdNegative() {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		// By default, any object returned from one of the methods called from the mock client dao will return null
		// So here we are not specifying any when(...).thenReturn(...);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT AND ASSERT
		 */
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			clientService.getClientById("1.0"); // ACT
		});
		
		
	}
	
	/*
	 * ClientService's editFirstNameOfClient(String clientId, String changedName)
	 */
	
	// Positive (happy path)
	@Test
	public void testEditFirstNameOfClientPositive() throws SQLException, ClientNotFoundException, InvalidParameterException {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		when(mockClientDao.getClientById(eq(5))).thenReturn(new Client(5, "Julie", "Smith","Regular", 225566666));
		
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Shelly", "Smith", "Regular", 18);
		when(mockClientDao.updateClient(eq(5), eq(dto))).thenReturn(new Client(5, "Shelly", "Smith", "Regular",225566666));
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT
		 */
		Client actual = clientService.editFirstNameOfClient("5", "Shelly");
		
		/*
		 * ASSERT
		 */
		Client expected = new Client(5, "Shelly", "Smith", "Regular", 225566666); // We expect the firstName property to be Ashley at this point
		
		Assertions.assertEquals(expected, actual);
		
	}
	
	// Negative
	// ClientNotFoundException case
	@Test
	public void testEditFirstNameOfClientButClientWithId10DoesNotExist() {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		// mocked methods that return objects will return null by default
		// so we don't need to worry about when(...).thenReturn(null);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT and ASSERT
		 */
		
		Assertions.assertThrows(ClientNotFoundException.class, () -> {
			
			clientService.editFirstNameOfClient("10", "Bill"); // ACT
			
		});
	}
	
	// Negative
	// InvalidParameterException thrown
	@Test
	public void testEditFirstNameButIdProvidedIsNotAnInt() {
		/*
		 * ARRANGE
		 */
		ClientDAO mockClientDao = mock(ClientDAO.class);
		
		ClientService clientService = new ClientService(mockClientDao);
		
		/*
		 * ACT and ASSERT
		 */
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.editFirstNameOfClient("abcsdfsdfdssf3434", "Test"); // ACT
			
		});
		
	}
	
	/*
	 * ClientService's addClient(AddOrUpdateClientDTO dto) method
	 */
	
	// Positive (happy path)
	@Test
	public void testAddClientAllInformationCorrectInDTO() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
		
		AddOrUpdateClientDTO dtoIntoDao = new AddOrUpdateClientDTO("Timmy", "Sims", "Rewards", 5);
		
		when(clientDao.addClient(eq(dtoIntoDao))).thenReturn(new Client(100, "Timmy", "Sims", "Rewards", 5));
		
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Timmy", "Sims", "Rewards", 5);
		Client actual = clientService.addClient(dto);
		
		/*
		 * ASSERT
		 */
		Client expected = new Client(100, "Timmy", "Sims", "Rewards", 5);
		Assertions.assertEquals(expected, actual);
		
	}
	
	// Negative
	// Scenario: everything is correct except the firstName was left blank
	@Test
	public void testAddClientFirstNameBlankEverythingElseValid() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
				
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT and ASSERT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("      ", "Sims", "Rewards", 5);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.addClient(dto);
			
		});
		
	
	}
	
	// Negative
	// Scenario: everything is correct except the lastName was left blank
	@Test
	public void testAddClientLastNameBlankEverythingElseValid() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
				
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT and ASSERT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Timmy", "                 ", "Rewards", 5);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.addClient(dto);
			
		});
		
	
	}
	
	// Negative
	// Scenario: everything is correct except both the firstName and lastName were left blank
	@Test
	public void testAddClientFirstNameAndLastNameBlankEverythingElseValid() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
				
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT and ASSERT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("", "                 ", "Rewards", 5);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.addClient(dto);
			
		});
		
	}
	
	// Negative
	// Scenario: everything is correct except classification was invalidly spelled
	@Test
	public void testAddClientRewardsSpelledIncorrectlyEverythingElseValid() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
				
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT and ASSERT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Timmy", "    Sims             ", "Freshmon", 5);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.addClient(dto);
			
		});
		
	}
	
	// Negative
	// Scenario: everything is correct except age is negative
	@Test
	public void testAddClientAgeIsNegativeEverythingElseValid() throws InvalidParameterException, SQLException {
		/*
		 * ARRANGE
		 */
		ClientDAO clientDao = mock(ClientDAO.class);
				
		ClientService clientService = new ClientService(clientDao);
		
		/*
		 * ACT and ASSERT
		 */
		AddOrUpdateClientDTO dto = new AddOrUpdateClientDTO("Timmy", "    Sims             ", "Rewards", -100);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			
			clientService.addClient(dto);
			
		});
		
	}
	
	
}