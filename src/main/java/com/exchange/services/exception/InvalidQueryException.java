package com.exchange.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidQueryException extends Exception {

	private static final long serialVersionUID = -6128759549485475920L;

	public InvalidQueryException(String message) {
		super(message);
	}
}
