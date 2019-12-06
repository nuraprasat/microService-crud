package com.service.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class UserInformation {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String phoneNumber;
	
	@OneToMany(mappedBy = "userInformation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	private List<BankDetails> bankDetails;
	
	private transient ErrorModel errorModel;
}
