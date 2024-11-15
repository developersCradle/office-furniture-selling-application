package com.officesales.office_furniture_sales.exception;

public class ResourceNotFoundException extends RuntimeException {

    /**
	 * For when Resource not found.
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}