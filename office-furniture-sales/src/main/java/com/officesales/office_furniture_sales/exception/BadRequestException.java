package com.officesales.office_furniture_sales.exception;

public class BadRequestException extends RuntimeException {

    /**
	 * BadRequestException for request that go wrong URL.
	 */
	private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}