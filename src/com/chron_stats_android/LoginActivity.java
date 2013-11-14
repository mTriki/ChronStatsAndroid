package com.chron_stats_android;

import com.chron_stat_android.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/*******************************************************************************
 * LoginActivity.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Activité gérant l'authentification d'un utilisateur avant que celui-ci
 *       puisse utiliser les fonctionnalités de l'application.
 * 
 * @notes L'authentification ne fonctionne pas encore car aucun utilisateur avec
 *        mot de passe n'est présent dans la base de donné. Le mot de passe pour
 *        procéder est 1234 (l'utilisateur n'a pas d'importance)
 ******************************************************************************/
public class LoginActivity extends Activity {
	private static final String password = "1234";

	/***************************************************************************
	 * Ajoute un OnClickListener sur le bouton d'envoi du clavier afin de gérer
	 * l'authentification.
	 * 
	 * Ajoute un OnClickListener sur le bouton d'envoi afin de gérer
	 * l'authentification.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 **************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Enregistre l'action listener pour le bouton d'envoi du clavier
		EditText editText = (EditText) findViewById(R.id.editText_password);
		editText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					sendLogin();
					handled = true;
				}
				return handled;
			}
		});

		// Enregistre l'action listener pour le bouton Envoyer...
		((Button) findViewById(R.id.button_sendLogin))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sendLogin();
					}
				});
	}

	/***************************************************************************
	 * Gère l'envoi de l'authentification au serveur
	 * 
	 * Ici, contrôle seulement que le mot de passe est correct et passe à
	 * l'activité principale, sinon affiche un Toast informant que le mot de
	 * passe est erroné
	 **************************************************************************/
	private void sendLogin() {
		EditText passwordView = (EditText) findViewById(R.id.editText_password);
		if (passwordView.getText().toString().compareTo(password) == 0) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(),
					"Authentification échouée.\nLe mot de passe est erroné",
					Toast.LENGTH_SHORT).show();
		}
	}
}
