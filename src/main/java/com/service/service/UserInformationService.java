package com.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.exception.UserInformationNotFoundException;
import com.service.model.ErrorModel;
import com.service.model.UserInformation;
import com.service.repository.UserInformationRepository;

@Service
public class UserInformationService {

	@Autowired
	UserInformationRepository userInformationRepository;

	public List<UserInformation> findAllUsers() {
		return userInformationRepository.findAll();
	}
	
	public Optional<UserInformation> findById(Integer id) {
		return userInformationRepository.findById(id);
	}

	public void deleteById(Integer id) {
		userInformationRepository.deleteById(id);
	}

	public UserInformation save(UserInformation userInformation) {
		return userInformationRepository.save(userInformation);
	}

	public ResponseEntity<Object> updateUser(UserInformation userInformation, Integer id) {
		Optional<UserInformation> userOptional = userInformationRepository.findById(id);
		if (!userOptional.isPresent())
			return ResponseEntity.notFound().build();
		
		userInformation.setUserId(userOptional.get().getUserId());
		userInformationRepository.save(userInformation);
		
		return ResponseEntity.ok().build();
	}



	public UserInformation updateUser(String email, Integer id) throws UserInformationNotFoundException {
		Optional<UserInformation> userOptional = userInformationRepository.findById(id);
		if (!userOptional.isPresent()) {
			ErrorModel em = new ErrorModel(HttpStatus.NOT_FOUND.value(), "user id not found");
			throw new UserInformationNotFoundException(em);
		}
		UserInformation userInformation = userOptional.get();
		userInformation.setEmail(email);
		userInformationRepository.save(userInformation);

		return userInformation;
	}

}
