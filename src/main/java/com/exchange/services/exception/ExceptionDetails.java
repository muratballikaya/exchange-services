package com.exchange.services.exception;

import java.util.Date;

public class ExceptionDetails {

	private Date exceptionDate;
	private String message;
	private String description;

	public ExceptionDetails(Date exceptionDate, String message, String description) {
		this.description = description;
		this.message = message;
		this.exceptionDate = exceptionDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescrition(String detailedMessage) {
		this.description = detailedMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(Date exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

	public String getInfo() {
		return "This is custom exception";
	}
}
