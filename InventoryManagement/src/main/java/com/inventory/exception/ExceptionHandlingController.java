package com.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(InvalidStockQuantityException.class)
	private ResponseEntity<ErrorResponse> resourceNotFound(InvalidStockQuantityException ex) {
		ErrorResponse response = new ErrorResponse("");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(StockInSufficientException.class)
	private ResponseEntity<StockInsufficientErrorResponse> insufficientFunds(StockInSufficientException ex) {
		StockInsufficientErrorResponse response = new StockInsufficientErrorResponse(
				"Ordered quantity is more than the available stock for the following products.Order according to available stock shown",
				ex.getStockNotsufficientList());
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
}
