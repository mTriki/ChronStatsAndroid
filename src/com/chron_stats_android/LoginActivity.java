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

public class LoginActivity extends Activity {
	private static final String password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Enregistre l'action listener pour le bouton d'envoi du clavier
        EditText editText = (EditText) findViewById(R.id.editText_password);
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendLogin();
                    handled = true;
                }
                return handled;
            }
        });
        
        // Enregistre l'action listener pour le bouton Envoyer...
        ((Button)findViewById(R.id.button_sendLogin)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendLogin();
			}
		});
    }

    private void sendLogin() {
    	EditText passwordView = (EditText)findViewById(R.id.editText_password);
    	if (passwordView.getText().toString().compareTo(password) == 0) {
    		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    		startActivity(intent);
    	}
    	else {
    		Toast.makeText(getApplicationContext(),"Authentification échouée.\nLe mot de passe est erroné",Toast.LENGTH_SHORT).show();
    	}
    }
}
