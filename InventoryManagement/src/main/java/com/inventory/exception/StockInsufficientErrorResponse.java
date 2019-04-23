package com.inventory.exception;

import java.util.List;

import com.inventory.dto.OrderLine;

public class StockInsufficientErrorResponse {

	private String message;

	private List<OrderLine> stockNotsufficientList;

	public List<OrderLine> getStockNotsufficientList() {
		return stockNotsufficientList;
	}

	public void setStockNotsufficientList(List<OrderLine> stockNotsufficientList) {
		this.stockNotsufficientList = stockNotsufficientList;
	}

	public StockInsufficientErrorResponse(String message, List<OrderLine> stockNotsufficientList) {
		super();
		this.message = message;
		this.stockNotsufficientList = stockNotsufficientList;
	}

	public String getMessage() {
		return message;
	}

	

}