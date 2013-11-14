package com.chron_stats_android.tasks;

import java.net.URI;
import java.net.URISyntaxException;

import com.chron_stats_android.model.User;
import com.google.gson.Gson;

/*******************************************************************************
 * Request.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Classe utilitaire contenant les informations générale d'une requête
 *       HTTP afin d'envoyer un JSON sur le serveur.
 * 
 * @notes -
 ******************************************************************************/
public class Request {

	// L'url cible de la requête.
	private String url;

	// La méthode HTTP utilisée, selon CRUD.
	private String method; // POST, PUT or DELETE

	// L'utilisateur cible de la requête.
	private User user;

	/*
	 * La classe de la personne à sérialiser en JSON. Initialement prévu pour le
	 * polymorphisme.
	 */
	private Class<?> typeOfPerson;

	/***************************************************************************
	 * Constructeur de requête.
	 * 
	 * @param url
	 *            L'URL cible de la requête.
	 * @param method
	 *            La méthode HTTP de la requête.
	 * @param user
	 *            L'utilisateur cible de la requête.
	 * @param typeOfPerson
	 *            La classe de la personne cible. Ici, seul User.class est
	 *            utilisée de par l'absence de polymorphisme.
	 **************************************************************************/
	public Request(String url, String method, User user, Class<?> typeOfPerson) {
		this.url = url;
		this.method = method;
		this.user = user;
		this.typeOfPerson = typeOfPerson;
	}

	/***************************************************************************
	 * Convertit l'URL de la requête en URI pour l'utilisation du client HTTP du
	 * package org.apache.http.client
	 * 
	 * @return L'URI construite à partir de l'URL de cette requête.
	 * @throws URISyntaxException
	 **************************************************************************/
	public URI getURI() throws URISyntaxException {
		return new URI(url);
	}

	/***************************************************************************
	 * Getter de la méthode de la requête.
	 * 
	 * @return La méthode HTTP de la requête sous forme de chaîne de caractère.
	 **************************************************************************/
	public String getMethod() {
		return this.method;
	}

	/***************************************************************************
	 * Sérialise l'utilisateur de cette requête en format JSON.
	 * 
	 * @return Le JSON de l'utilisateur de la requête sous forme de chaîne de
	 *         caractères.
	 **************************************************************************/
	public String getPersonJSON() {
		Gson gson = new Gson();
		return gson.toJson(user, typeOfPerson);
	}
}
