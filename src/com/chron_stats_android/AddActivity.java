package com.chron_stats_android;

import com.chron_stat_android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/*******************************************************************************
 * AddActivity.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Activité utilisée uniquement dans la vue mobile, contenant le fragment
 *       d'ajout d'utilisateur.
 * 
 * @notes -
 ******************************************************************************/
public class AddActivity extends Activity {
	/***************************************************************************
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 **************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		/* DEADCODE: test de code mort
		 * Pris en charge dans le onActivityCreated() du fragment
		// Efface le contenu du fragment d'ajout
		AddFragment addFragment = (AddFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_add);
		addFragment.clearContent();
		*/
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
