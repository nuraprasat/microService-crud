package com.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

		userInformationRepository.save(userInformation);
		return null;
	}

}
