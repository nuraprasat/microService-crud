package com.service.exception;

import com.service.model.ErrorModel;

public class UserInformationNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private ErrorModel e;

	public UserInformationNotFoundException(ErrorModel e) {
		this.e = e;
	}
	
	public ErrorModel getErrorModel() {
		return this.e;
	}

}
