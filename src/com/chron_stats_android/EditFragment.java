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
 * EditFragment.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Fragment d'édition d'utilisateur
 * 
 *       Gère l'édition et la suppression d'utilisateurs dans la base de données
 *       du serveur Ruby on Rails (Prototype: gère uniquement la modification et
 *       suppression d'utilisateurs).
 * 
 *       Contient deux champs de texte "nom" et "email", un bouton de validation
 *       d'édition et un bouton de suppression de l'utilisateur courant.
 * 
 * @notes Non-polymorphique: gère uniquement la classe User
 ******************************************************************************/
public class EditFragment extends Fragment implements
		SendJSONTask.CallBackListener {
	// L'utilisateur à éditer ou à supprimer
	private User editUser;

	/***************************************************************************
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 **************************************************************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_edit, container, false);
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
	 * Ajoute un OnClickListener sur le bouton d'édition afin de gérer l'édition
	 * des utilisateurs sur le serveur.
	 * 
	 * Ajoute un OnClickListener sur le bouton de suppression afin de gérer la
	 * suppression des utilisateurs sur le serveur.
	 * 
	 * Met à jour le contenu du fragment avec l'utilisateur à éditer.
	 * 
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 **************************************************************************/
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((Button) getActivity().findViewById(R.id.button_edit))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						editUser();
						refreshList();
						discardFragment();
					}
				});

		((Button) getActivity().findViewById(R.id.button_delete))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						deleteUser();
						refreshList();
						discardFragment();
					}
				});

		/*
		 * Si on se trouve dans la vue tablette (ce fragment est dans
		 * MainActivity), met à jour le contenu avec l'utilisateur désiré à la
		 * création
		 */
		User user = (User) getArguments().getSerializable("user");
		if (user != null) {
			updateContent(user);
		}
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

	/***************************************************************************
	 * Si on se trouve dans la vue tablette, enlève ce fragment de la vue
	 **************************************************************************/
	public void discardFragment() {
		Context context = getActivity();
		if (context instanceof MainActivity) {
			((MainActivity) context).discardEditFragment();
		}
	}

	/***************************************************************************
	 * Modifie un utilisateur dans le serveur en démarrant une SendJSONTask et
	 * en lui passant le contenu des champs de texte.
	 **************************************************************************/
	public void editUser() {
		// Définition de l'adresse du serveur
		String url = MainActivity.SERVER_URL + "users/" + editUser.getID();

		// Méthode de la requête HTTP à envoyer (selon CRUD: Update = PUT)
		String method = "PUT";

		// Création de l'utilisateur modifié à partir des champs de texte
		User user = (User) editUser;
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
	 * Supprime un utilisateur du serveur en démarrant une SendJSONTask et en
	 * lui passant le contenu des champs de texte.
	 **************************************************************************/
	public void deleteUser() {
		// Définition de l'adresse du serveur
		String url = MainActivity.SERVER_URL + "users/" + editUser.getID();

		// Méthode de la requête HTTP à envoyer (selon CRUD: Delete = DELETE)
		String method = "DELETE";

		// Création de l'utilisateur à supprimer à partir des champs de texte
		User user = (User) editUser;

		// Création de la SendJSONTask
		SendJSONTask task = new SendJSONTask();
		task.setListener(this);
		task.execute(new Request(url, method, user, User.class));
	}

	/***************************************************************************
	 * Met à jout le contenu du fragment avec les informations de l'utilisateur
	 * passée en paramètre.
	 * 
	 * @param user
	 *            L'utilisateur avec lequelle mettre à jour le contenu du
	 *            fragment
	 **************************************************************************/
	public void updateContent(User user) {
		this.editUser = user;

		((EditText) getActivity().findViewById(R.id.editText_name))
				.setText(user.getName());
		((EditText) getActivity().findViewById(R.id.editText_email))
				.setText(user.getEmail());
	}

	/****************************************************************************
	 * Callback appelé par la SendJSONTask quand le traitement des requêtes est
	 * fini. Affiche un Toast informant de la terminaison de la requête
	 * 
	 * @see com.chron_stats_android.tasks.SendJSONTask.CallBackListener#callback(java.lang.String)
	 ***************************************************************************/
	@Override
	public void callback(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}
