package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.Person;
import com.chron_stats_android.model.User;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends Activity implements
		PersonListFragment.OnItemClickListener {
	public static final String SERVER_URL = "http://chron-stats.herokuapp.com/";
	public static final String JSON_EXTENSION = ".json";
	private AddFragment addFragment = null;
	private EditFragment editFragment = null;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add:
			addAction();
			return true;
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PersonListFragment personListFragment = (PersonListFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_personList);
		personListFragment.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PersonListFragment plf = (PersonListFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_personList);
		plf.refreshList();
	}

	/**
	 * Le callback que le fragment de liste de personne appelle quand un élément
	 * est cliqué
	 */
	@Override
	public void onItemClick(Person person) {
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout_addEditFragment);
		FragmentManager fm = getFragmentManager();
		editFragment = (EditFragment) fm.findFragmentById(R.id.fragment_edit);
		addFragment = (AddFragment) fm.findFragmentById(R.id.fragment_add);
		if (frameLayout == null) {
			/*
			 * EditFragment ne se trouve pas dans le layout ou que l'orientation
			 * est verticale, il faut alors démarrer l'activité EditActivity en
			 * lui passant la personne à éditer
			 */
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra("person", (User) person);
			startActivity(intent);
		} else if (editFragment != null) {
			/*
			 * EditFragment est dans le layout, il faut alors lui dire de mettre
			 * à jour sa vue.
			 */
			editFragment.updateContent(person);
		} else if (addFragment != null) {
			/*
			 * AddFragment est dans le layout, il faut alors le remplacer par un
			 * EditFragment
			 */
			FragmentTransaction ft = fm.beginTransaction();
			editFragment = new EditFragment();

			ft.replace(R.id.frameLayout_addEditFragment, editFragment);
			ft.addToBackStack(null);
			ft.commit();
			addFragment = null;
		}
	}

	public void addAction() {
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout_addEditFragment);
		FragmentManager fm = getFragmentManager();
		editFragment = (EditFragment) fm.findFragmentById(R.id.fragment_edit);
		addFragment = (AddFragment) fm.findFragmentById(R.id.fragment_add);
		if (frameLayout == null) {
			/*
			 * EditFragment ne se trouve pas dans le layout ou que l'orientation
			 * est verticale, il faut alors démarrer l'activité EditActivity en
			 * lui passant la personne à éditer
			 */
			Intent intent = new Intent(this, AddActivity.class);
			startActivity(intent);
		} else if (editFragment != null) {
			/*
			 * EditFragment est dans le layout, il faut alors le remplacer par
			 * un AddFragment
			 */
			FragmentTransaction ft = fm.beginTransaction();
			addFragment = new AddFragment();

			ft.replace(R.id.frameLayout_addEditFragment, addFragment);
			ft.addToBackStack(null);
			ft.commit();
			editFragment = null;
		} else if (addFragment != null) {
			/*
			 * AddFragment est dans le layout, il faut alors effacer son
			 * contenu.
			 */
			addFragment.clearContent();
		}
	}
}
