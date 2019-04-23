package com.inventory.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table
public class Product{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column
	private String productName;
	
	@Column
	private String productDesc;
	
	@Column
	private int price;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy="product")	
	private Stock stock;

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
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	

}
