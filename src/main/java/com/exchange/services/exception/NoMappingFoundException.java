package com.exchange.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoMappingFoundException extends Exception{

	private static final long serialVersionUID = -7628001021457542602L;
	
	public NoMappingFoundException(String message) {
		super(message);
	}

}
