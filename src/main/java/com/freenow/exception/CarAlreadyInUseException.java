package com.freenow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Specified Car is already in Use.")
public class CarAlreadyInUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public CarAlreadyInUseException(String message)
	 {
	      super(message);
	 }

}
