package com.service.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.exception.UserInformationNotFoundException;
import com.service.model.ErrorModel;
import com.service.model.UserInformation;
import com.service.service.UserInformationService;

@RestController
public class ControllerRest {

	@Autowired
	UserInformationService userInformationService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserInformation>> retrieveAllStudents() {
		return new ResponseEntity<>(userInformationService.findAllUsers(), HttpStatus.OK);
	}
	
	@Validated
	@GetMapping("/getUserInformation/{id}")
	public ResponseEntity<UserInformation> retrieveUser(@PathVariable @Min(1) Integer id) throws UserInformationNotFoundException {
		Optional<UserInformation> userInformation = userInformationService.findById(id);

		if (!userInformation.isPresent()) {
			ErrorModel em = new ErrorModel(HttpStatus.BAD_REQUEST.value(), "user id - " + id +" not found");
			throw new UserInformationNotFoundException(em);
		}

		return new ResponseEntity<>(userInformation.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public void deleteStudent(@PathVariable Integer id) {
		userInformationService.deleteById(id);
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<UserInformation> createStudent(@RequestBody UserInformation userInformation) throws UserInformationNotFoundException {
		UserInformation savedStudent = userInformationService.save(userInformation);

		if(savedStudent == null) {
			ErrorModel em = new ErrorModel(HttpStatus.CONFLICT.value(), "user id already exists");
			throw new UserInformationNotFoundException(em);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody UserInformation userInformation, @PathVariable Integer id) {
		userInformationService.updateUser(userInformation, id);
		return ResponseEntity.noContent().build();
	}
}
