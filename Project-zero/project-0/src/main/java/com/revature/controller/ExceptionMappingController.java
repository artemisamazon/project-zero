package com.revature.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.dto.ExceptionMessageDTO;
import com.revature.exceptions.InvalidParameterException;
import com.revature.exceptions.ClientNotFoundException;

import io.javalin.Javalin;

// When we have exceptions that propagate all the way to our Handlers in the controller layer, we can instead of using try {} catch () {} 
// utilize Javalin's exception mapping functionality
// This allows us to map exceptions that may make it's way to the controller layer, and then handle them within a centralized location.
// This prevents code redundancy and duplication
public class ExceptionMappingController {

	public void mapExceptions(Javalin app) {
		app.exception(UnrecognizedPropertyException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new ExceptionMessageDTO(e));
		});
		
		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.json(new ExceptionMessageDTO(e));
			ctx.status(400);
		});
		
		// For example, in the cases where we want to modify or delete or get a student who does not exist (PUT /students/10000, DELETE /students/10000, 
		// GET /students/10000), that should result in some kind of ClientNotFoundException being thrown in the service layer.
		// When it propagates to the controller layer, that is when we can go ahead and specify a 404 Not found HTTP status code (and an appropriate exception message
		// that is encapsulated via a DTO (ExceptionMessageDTO))
		app.exception(ClientNotFoundException.class, (e, ctx) -> {
			ctx.json(new ExceptionMessageDTO(e));
			ctx.status(404);
		});
		
	}
	
}