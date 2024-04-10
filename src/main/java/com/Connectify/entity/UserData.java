package com.Connectify.entity;


public class UserData {
	private String name;
	private String email;
	private String password;
	private boolean paidPlan;
	
	public UserData() {
	}
	
	public UserData(String name, String email,String password, boolean paidPlan) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.paidPlan = paidPlan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPaidPlan() {
		return paidPlan;
	}

	public void setPaidPlan(boolean paidPlan) {
		this.paidPlan = paidPlan;
	}
	
}
