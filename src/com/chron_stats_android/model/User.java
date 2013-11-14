package com.chron_stats_android.model;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -215331534484631803L;

	// ID de l'utilisateur dans la base de donnée du serveur
	private int id = 0;

	// Nom de l'utilisateur
	private String nom = "user";

	// E-mail de l'utilisateur
	private String email;

	/***************************************************************************
	 * Constructeur vide. Initialise les membres aux valeurs par défaut.
	 **************************************************************************/
	public User() {
		this.id = 0;
		this.nom = "user";
		this.email = "user@domain.com";
	}

	/***************************************************************************
	 * Constructeur prenant une chaîne de caractères contenant le nom de
	 * l'utilisateur à créer
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur.
	 **************************************************************************/
	public User(String nom) {
		this.id = 0;
		this.nom = nom;
		this.email = nom + "@domain.com";
	}

	/***************************************************************************
	 * Constructeur d'utilisateur avec nom et adresse e-mail.
	 * 
	 * @param nom
	 *            Le nom de l'utilisateur.
	 * @param email
	 *            L'adresse e-mail de l'utilisateur.
	 **************************************************************************/
	public User(String nom, String email) {
		this.id = 0;
		this.nom = nom;
		this.email = email;
	}
	
	/***************************************************************************
	 * Getter de l'identificateur.
	 * 
	 * @return L'identificateur de cet utilisateur.
	 **************************************************************************/
	public int getID() {
		return this.id;
	}

	/***************************************************************************
	 * Getter du nom.
	 * 
	 * @return Le nom de cet utilisateur.
	 **************************************************************************/
	public String getName() {
		return this.nom;
	}
	
	/***************************************************************************
	 * Setter du nom.
	 * 
	 * @param name
	 * 		Le nouveau nom de cet utilisateur.
	 **************************************************************************/
	public void setName(String name) {
		this.nom = name;
	}

	/***************************************************************************
	 * Getter de l'adresse e-mail.
	 * 
	 * @return L'adresse e-mail de cet utilisateur.
	 **************************************************************************/
	public String getEmail() {
		return this.email;
	}

	/***************************************************************************
	 * Setter de l'adresse e-mail.
	 * 
	 * @param email
	 *            La nouvelle adresse e-mail de cet utilisateur.
	 **************************************************************************/
	public void setEmail(String email) {
		this.email = email;
	}

	/***************************************************************************
	 * Méthode toString redéfinie pour représenter un utilisateur de façon
	 * lisible.
	 * 
	 * @return La représentation en chaîne de caractères de l'utilisateur.
	 * 
	 * @see java.lang.Object#toString()
	 **************************************************************************/
	@Override
	public String toString() {
		return super.toString() + " : " + this.email;
	}
}
