package com.sample;

import java.util.ArrayList;

public class Customer {

	private int age;
	private String name;
	private String gender;
	private String city;
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int getAge(){
		return this.age;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}
	
	public String getGender(){
		return this.gender;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setOrders(ArrayList<Order> orders){
		this.orders = orders;
	}
	
	public ArrayList<Order> getOrders(){
		return this.orders;
	}
	
	public String toString(){
		return "\n------------\nName: "+this.getName()+"\nAge: "+this.getAge()+"\nGender: "+this.getGender()+"\nCity: "+this.getCity()+"\n------------";
	}

}
