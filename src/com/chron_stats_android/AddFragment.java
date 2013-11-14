package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.User;
import com.chron_stats_android.tasks.Request;
import com.chron_stats_android.tasks.SendJSONTask;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*******************************************************************************
 * AddFragment.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Fragment d'ajout d'utilisateur
 * 
 *       Gère l'ajout d'utilisateurs à la base de données du serveur Ruby on
 *       Rails (Prototype: gère uniquement l'ajout d'utilisateurs).
 * 
 *       Contient deux champs de texte "nom" et "email" et un bouton de
 *       validation de l'ajout.
 * 
 * @notes Non-polymorphique: gère uniquement la classe User
 ******************************************************************************/
public class AddFragment extends Fragment implements
		SendJSONTask.CallBackListener {
	/***************************************************************************
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 **************************************************************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_add, container, false);
	}

	/***************************************************************************
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 **************************************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/***************************************************************************
	 * Callback appelé par l'activité qui contient ce fragment quand celle-ci a
	 * fini sa méthode onCreate().
	 * 
	 * Ajoute un OnClickListener sur le bouton d'ajout afin de gérer l'ajout
	 * d'utilisateurs sur le serveur.
	 * 
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 **************************************************************************/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((Button) getActivity().findViewById(R.id.button_add))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						addUser();
						refreshList();
						discardFragment();
					}
				});

		// Efface le contenu des champs
		clearContent();
	}

	/***************************************************************************
	 * Si on se trouve dans la vue tablette, rafraichit la liste d'utilisateurs
	 **************************************************************************/
	public void refreshList() {
		UserListFragment plf = (UserListFragment) getActivity()
				.getFragmentManager().findFragmentById(R.id.fragment_userList);
		if (plf != null) {
			plf.refreshList();
		}
		/*
		 * DEAD CODE TEST Context context = getActivity(); if (context
		 * instanceof MainActivity) { ((MainActivity) context).refreshList(); }
		 */
	}

	/****************************************************************************
	 * Si on se trouve dans la vue tablette, enlève ce fragment de la vue
	 **************************************************************************/
	public void discardFragment() {
		Context context = getActivity();
		if (context instanceof MainActivity) {
			((MainActivity) context).discardAddFragment();
		}
	}

	/****************************************************************************
	 * Ajoute un utilisateur au serveur en démarrant une SendJSONTask et en lui
	 * passant le contenu des champs de texte.
	 **************************************************************************/
	public synchronized void addUser() {
		// Définition de l'adresse du serveur
		String url = MainActivity.SERVER_URL + "users";

		// Méthode de la requête HTTP à envoyer (selon CRUD: Create = POST)
		String method = "POST";

		// Création du nouvel utilisateur à partir des champs de texte
		User user = new User();
		String newName = ((EditText) getActivity().findViewById(
				R.id.editText_name)).getText().toString();
		String newEmail = ((EditText) getActivity().findViewById(
				R.id.editText_email)).getText().toString();
		user.setName(newName);
		user.setEmail(newEmail);

		// Création de la SendJSONTask
		SendJSONTask task = new SendJSONTask();
		task.setListener(this);
		task.execute(new Request(url, method, user, User.class));
	}

	/***************************************************************************
	 * Efface le contenu des champs de texte du fragment.
	 ***************************************************************************/
	public void clearContent() {
		((EditText) getActivity().findViewById(R.id.editText_name)).setText("");
		((EditText) getActivity().findViewById(R.id.editText_email))
				.setText("");
	}

	/****************************************************************************
	 * Callback appelé par la SendJSONTask quand le traitement des requêtes est
	 * fini.
	 * 
	 * @see com.chron_stats_android.tasks.SendJSONTask.CallBackListener#callback(java.lang.String)
	 ***************************************************************************/
	@Override
	public void callback(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}