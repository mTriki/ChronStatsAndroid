package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		User person = (User)getIntent().getExtras().get("person");
		
		EditFragment editFragment = (EditFragment)getFragmentManager().findFragmentById(R.id.fragment_edit);
		editFragment.updateContent(person);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
