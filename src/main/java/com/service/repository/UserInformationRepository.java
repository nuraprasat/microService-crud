package com.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.UserInformation;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer>{

}
