package com.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorModel {
	private int errorCode;
	private String message;
}
