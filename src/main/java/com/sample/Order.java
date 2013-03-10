package com.sample;

public class Order {
	
	private int id;
	private String name;
	private Customer customer;
	private double price;
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setCustomer(Customer customer){
		this.customer = customer;
	}
	
	public Customer getCustomer(){
		return this.customer;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return this.price;
	}
}
