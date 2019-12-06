package com.service.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.service.exception.UserInformationNotFoundException;
import com.service.model.BankDetails;
import com.service.model.UserInformation;
import com.service.repository.UserInformationRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserInformationServiceTest {

	@InjectMocks
	UserInformationService userInformationService;
	
	@Mock
	UserInformationRepository userInformationRepository;
	
	@Test
	public void checkUpdateUserWithReturnData() throws Exception {
		Mockito.when(userInformationRepository.findById(1)).thenReturn(optionalUserInformation());
		Mockito.when(userInformationRepository.save(Mockito.any(UserInformation.class))).thenReturn(null);
		ResponseEntity<Object> re = userInformationService.updateUser(getListOfProduct().get(0), 1);
		assertEquals(HttpStatus.OK, re.getStatusCode());
		List<UserInformation> uList = getListOfProduct();
		uList.get(0).setUserId(optionalUserInformation().get().getUserId());
		Mockito.verify(userInformationRepository, Mockito.times(1)).save(uList.get(0));
	}
	
	@Test
	public void checkUpdateUserWithNoReturnData() throws Exception {
		Mockito.when(userInformationRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		Mockito.when(userInformationRepository.save(Mockito.any(UserInformation.class))).thenReturn(null);
		ResponseEntity<Object> re = userInformationService.updateUser(getListOfProduct().get(0), 1);
		assertEquals(HttpStatus.NOT_FOUND, re.getStatusCode());
		List<UserInformation> uList = getListOfProduct();
		uList.get(0).setUserId(optionalUserInformation().get().getUserId());
		Mockito.verify(userInformationRepository, Mockito.times(0)).save(uList.get(0));
	}
	
	@Test
	public void checkUpdateUserForPatch() throws Exception {
		Mockito.when(userInformationRepository.findById(1)).thenReturn(optionalUserInformation());
		userInformationService.updateUser("abc@a", 1);
		UserInformation uList = optionalUserInformation().get();
		uList.setEmail("abc@a");
		Mockito.verify(userInformationRepository, Mockito.times(1)).save(uList);
		
	}
	
	@Test(expected = UserInformationNotFoundException.class)
	public void checkUpdateUserForPatchThrowsException() throws Exception {
		Mockito.when(userInformationRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		userInformationService.updateUser("abc@a", 1);
	}
	
	private Optional<UserInformation> optionalUserInformation() {
		Optional<UserInformation> optionalUserInformation = null;
		UserInformation userInformation = new UserInformation();
		userInformation.setBankDetails(getBankDetails());
		userInformation.setEmail("abc@abc.com");
		userInformation.setFirstName("a");
		userInformation.setLastName("b");
		userInformation.setPhoneNumber("87543210");
		userInformation.setUserId(1);
		optionalUserInformation = Optional.of(userInformation);
		return optionalUserInformation;
	}
	
	private List<UserInformation> getListOfProduct() {
		List<UserInformation> userDetailsList = new ArrayList<>();
		UserInformation userInformation = new UserInformation();
		userInformation.setBankDetails(getBankDetails());
		userInformation.setEmail("abc@abc.com");
		userInformation.setFirstName("a");
		userInformation.setLastName("b");
		userInformation.setPhoneNumber("87543210");
		userInformation.setUserId(1);
		userDetailsList.add(userInformation);
		return userDetailsList;
	}

	private List<BankDetails> getBankDetails() {
		List<BankDetails> list = new ArrayList<>();
		BankDetails bankDetails = new BankDetails();
		bankDetails.setAccountNumber("123abc");
		bankDetails.setBalance(120.0);
		bankDetails.setBankName("icici");
		list.add(bankDetails);
		list.add(bankDetails);
		return list;
	}
}
