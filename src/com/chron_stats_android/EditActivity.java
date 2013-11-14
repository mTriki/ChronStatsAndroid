package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/*******************************************************************************
 * EditFragment.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Activité utilisée uniquement dans la vue mobile, contenant le fragment
 *       d'édition d'utilisateur.
 * 
 * @notes -
 ******************************************************************************/
public class EditActivity extends Activity {
	/***************************************************************************
	 * A la création du fragment, on récupère l'utilisateur passée par l'activité
	 * appelante et on met à jour le contenu du fragment d'édition.
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 **************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);

		User user = (User) getIntent().getExtras().get("user");

		EditFragment editFragment = (EditFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_edit);
		editFragment.updateContent(user);
	}

	/***************************************************************************
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 **************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
