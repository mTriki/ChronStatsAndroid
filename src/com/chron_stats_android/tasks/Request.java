package com.chron_stats_android.tasks;

import java.net.URI;
import java.net.URISyntaxException;
import com.chron_stats_android.model.Person;
import com.google.gson.Gson;

public class Request {
	private String url;
	private String method; // POST, PUT or DELETE
	private Person person;
	private Class<?> typeOfPerson;
	
	public Request(String url, String method, Person person, Class<?> typeOfPerson) {
		this.url = url;
		this.method = method;
		this.person = person;
		this.typeOfPerson = typeOfPerson;
	}
	
	public URI getURI() throws URISyntaxException {
		return new URI(url);
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public String getPersonJSON() {
		Gson gson = new Gson();
		return gson.toJson(person, typeOfPerson);
	}
}
