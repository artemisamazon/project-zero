package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrUpdateClientDTO;
import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController {
	
	// Purpose of Controller layer is to communicate with Service layer.
	
	// Below is the dependency here for the Controller
	private ClientService clientService; // ClientController instance HAS-A ClientService
	
	public ClientController() {
		this.clientService = new ClientService();
	}
	
	private Handler editClientFirstName = (ctx) -> {
		
		// Extract the client's id from the URI path
		String clientId = ctx.pathParam("id");
				
		// Extracts the information in the form of JSON from the body and place it into an object of the type EditFirstNameDTO
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		
		// When we invoke the editFirstNameOfClient method (service layer), it will then contact the updateClient method in the DAO layer, which
		// will then return the Client object representation of the record that was newly updated
		Client clientThatWasJustEdited = this.clientService.editFirstNameOfClient(clientId, dto.getFirstName());
		
		// We then take that object and convert it into JSON
		// This JSON is then sent back to the client that sent the request (in our case, Postman)
		ctx.json(clientThatWasJustEdited); // For this to work, we need to have getter methods that exist for that object
		
	};
	
	private Handler addClient = (ctx) -> {
		AddOrUpdateClientDTO dto = ctx.bodyAsClass(AddOrUpdateClientDTO.class);
		
		Client s = this.clientService.addClient(dto);
		
		ctx.json(s);
		ctx.status(201); // 201 CREATED
	};
	
	private Handler getAllClients = (ctx) -> {
		List<Client> clients = this.clientService.getAllClients();
		
		ctx.json(clients);
	};
	
	private Handler getClientById = (ctx) -> {
		String id = ctx.pathParam("id");
		
		Client s = this.clientService.getClientById(id);
			
		ctx.json(s);
	};
	
	private Handler editClientById = (ctx) -> {
			

			
	
		
	};
	
	private Handler deleteClientById = (ctx) -> {
		String id = ctx.pathParam("id");
		
		this.clientService.deleteClientById(id);
	};
	
	private Handler deleteAllClients = (ctx) -> {
		
	};
	
	
	
	/*An API Endpoint is the URL for a server or a service. These APIs operate through responses and requests — 
	 * that is you make a request and the API Endpoint makes a response*/
	public void registerEndpoints(Javalin app) {
		app.patch("/clients/{id}/firstname", editClientFirstName);
		
		app.post("/clients", addClient);
		app.get("/clients", getAllClients);
		app.get("/clients/{id}", getClientById);
		app.put("/clients/{id}", editClientById);
		app.delete("/clients/{id}", deleteClientById);
		app.delete("/clients", deleteAllClients);
	}
	
}