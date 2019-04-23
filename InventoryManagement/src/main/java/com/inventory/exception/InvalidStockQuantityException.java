package com.inventory.exception;

public class InvalidStockQuantityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidStockQuantityException(String exception) {
		super(exception);

	}
}
