package com.service.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.exception.UserInformationNotFoundException;
import com.service.model.ErrorModel;
import com.service.model.UserInformation;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class RestErrorHandler {
	
	private static final String INTERNAL_SERVER_ERROR_LOG = "Internal Server Error - ";
	private static final Logger LOGGER = Logger.getLogger(RestErrorHandler.class.getName());
	
	@ExceptionHandler(UserInformationNotFoundException.class)
	public ResponseEntity<UserInformation> hadleInternalError(final UserInformationNotFoundException e) {
		LOGGER.log(Level.WARNING,INTERNAL_SERVER_ERROR_LOG+e.getErrorModel());
		return error(e.getErrorModel());
	}
	
	private ResponseEntity<UserInformation> error(final ErrorModel em) {
		UserInformation p = getProduct(em);
		return new ResponseEntity<UserInformation>(p, HttpStatus.BAD_REQUEST);
	}
	
	private UserInformation getProduct(ErrorModel em) {
		UserInformation p = new UserInformation();
		p.setErrorModel(em);
		return p;
	}

	
}
