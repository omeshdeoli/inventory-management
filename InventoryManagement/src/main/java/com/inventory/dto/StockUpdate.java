package com.inventory.dto;

import java.util.List;

import com.inventory.entity.Stock;

public class StockUpdate {

	private List<OrderLine> stockList;

	public List<OrderLine> getStockList() {
		return stockList;
	}

	public void setStockList(List<OrderLine> stockList) {
		this.stockList = stockList;
	}
}
