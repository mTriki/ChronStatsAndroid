package com.chron_stats_android.model;


public class User extends Person {
	private static final long serialVersionUID = -3542944536187114854L;
	private String email;
	
	public User() {
		super();
		this.email = "user@domain.com";
	}
	
	public User(String email) {
		super(email.substring(0, email.indexOf("@")));
		this.email = email;
	}
	
	public User(String nom, String email) {
		super(nom);
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return super.toString()+" : "+this.email;
	}
}
