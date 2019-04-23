package com.inventory.exception;

import java.util.List;

import com.inventory.dto.OrderLine;

public class StockInSufficientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<OrderLine> stockNotsufficientList;

	public List<OrderLine> getStockNotsufficientList() {
		return stockNotsufficientList;
	}

	public void setStockNotsufficientList(List<OrderLine> stockNotsufficientList) {
		this.stockNotsufficientList = stockNotsufficientList;
	}

	public StockInSufficientException(List<OrderLine> stockNotsufficientList) {
		super();
		this.stockNotsufficientList = stockNotsufficientList;
	}

	public StockInSufficientException(String exception) {
		super(exception);

	}
}
