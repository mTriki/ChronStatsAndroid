package com.chron_stats_android.model;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 5750912299819892563L;
	private int id = 0;
	private String nom = "user";
	
	public Person() {
		super();
		this.nom = "user";
	}
	
	public Person(String nom) {
		this.nom = nom;
	}
	
	public String getName() {
		return this.nom;
	}
	
	public void setName(String name) {
		this.nom = name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String toString() {
		return this.id+":"+this.nom;
	}
}
