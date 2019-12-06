package com.service.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.model.BankDetails;
import com.service.model.UserInformation;
import com.service.service.UserInformationService;

@RunWith(MockitoJUnitRunner.class)
public class ControllerRestTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Mock
	private UserInformationService userInformationService;
	
	@InjectMocks
	ControllerRest controllerRest;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controllerRest).build();
	}

	@Test
	public void AllProductShouldBeReturnedFromService() throws Exception {
		Mockito.when(userInformationService.findAllUsers()).thenReturn(getListOfProduct());
		MvcResult result = this.mockMvc.perform(get("/getAllUsers").accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
		ObjectMapper mapper = new ObjectMapper();
		List<UserInformation> proList = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<UserInformation>>() {});
		assertNotNull(proList.get(0));
		assertEquals(getListOfProduct().get(0), proList.get(0));
	}
	
	@Test
	public void productShouldBeReturnedbasedOnId() throws Exception {
		Mockito.when(userInformationService.findById(Mockito.anyInt())).thenReturn(optionalUserInformation());
		MvcResult result = this.mockMvc.perform(get("/getUserInformation/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
		ObjectMapper mapper = new ObjectMapper();
		UserInformation proList = mapper.readValue(result.getResponse().getContentAsString(), UserInformation.class);
		assertNotNull(proList);
		assertEquals(getListOfProduct().get(0), proList);
	}
	
	@Test(expected=NestedServletException.class)
	public void productOptionalNotReturnedbasedOnId() throws Exception {
		Optional<UserInformation> optionalUserInformation = Optional.ofNullable(null);
		Mockito.when(userInformationService.findById(Mockito.anyInt())).thenReturn(optionalUserInformation);
		this.mockMvc.perform(get("/getUserInformation/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
	}
	
	@Test
	public void checkDeleteUser() throws Exception {
		Mockito.doNothing().when(userInformationService).deleteById(Mockito.anyInt());
		this.mockMvc.perform(delete("/deleteUser/{id}",1).accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
	}
	
	@Test
	public void checkPatchUserWorking() throws Exception {
		Mockito.when(userInformationService.updateUser(Mockito.anyString(), Mockito.anyInt())).thenReturn(getListOfProduct().get(0));
		MvcResult result = this.mockMvc.perform(patch("/updateUser/{id}",1).param("email", "bcd@xyz.com").accept(MediaType.APPLICATION_JSON_VALUE))
														.andExpect(status().is(200))
														.andReturn();
		ObjectMapper mapper = new ObjectMapper();
		UserInformation proList = mapper.readValue(result.getResponse().getContentAsString(), UserInformation.class);
		assertNotNull(proList);
		assertEquals(getListOfProduct().get(0).getEmail(), "abc@abc.com");
	}
	
	@Test
	public void checkPostCreateUserWorking() throws Exception {
		Mockito.when(userInformationService.updateUser(Mockito.any(UserInformation.class), Mockito.anyInt())).thenReturn(null);
		this.mockMvc.perform(put("/updateUser/{id}",1)
														.contentType(APPLICATION_JSON_UTF8)
														.content(getListOfProduct().get(0).toString()))
														.andExpect(status().is(400))
														.andReturn();
		Mockito.verify(userInformationService, Mockito.times(1)).updateUser(Mockito.anyString(), Mockito.anyInt());
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
