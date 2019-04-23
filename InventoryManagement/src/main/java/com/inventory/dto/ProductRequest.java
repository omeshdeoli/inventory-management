package com.inventory.dto;


public class ProductRequest {


	public String productName;
	

	public String productDesc;
	
	
	public int price;


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getProductDesc() {
		return productDesc;
	}


	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}
	
}
