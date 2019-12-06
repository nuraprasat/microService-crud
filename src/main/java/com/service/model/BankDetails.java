package com.service.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class BankDetails {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String bankName;
	private String accountNumber;
	private double balance;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_user_id", nullable = false, updatable = true, insertable = true)
	@JsonIgnore
	private UserInformation userInformation;
}
