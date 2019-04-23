package com.inventory.dto;


public class OrderLine {


	@Override
	public String toString() {
		return "OrderLine [productId=" + productId + ", quantity=" + quantity + "]";
	}


	public OrderLine(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}


	private Long productId;
	

	private int quantity;


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
