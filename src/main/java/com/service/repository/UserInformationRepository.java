package com.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.UserInformation;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer>{
	
	public List<UserInformation> findAll();
	public Optional<UserInformation> findById(Integer id);

}
