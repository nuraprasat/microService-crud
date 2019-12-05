package com.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.model.BankDetails;

public interface BankDetailsRepository extends JpaRepository<BankDetails, Integer> {

}
