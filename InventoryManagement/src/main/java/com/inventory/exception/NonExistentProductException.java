package com.inventory.exception;

public class NonExistentProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NonExistentProductException(String exception) {
		super(exception);

	}
}
