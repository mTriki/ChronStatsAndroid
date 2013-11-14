package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.User;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

/*******************************************************************************
 * MainActivity.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Activité principale de l'application Android. Affiche une liste
 *       d'utilisateurs récupérée depuis le serveur.
 * 
 *       Dans la vue mobile, la sélection d'un élément de la liste démarre une
 *       nouvelle activité (EditActivity dont l'unité fonctionnelle est un
 *       EditFragment) dans laquelle on pourra modifier ou supprimer
 *       l'utilisateur sélectionné du serveur. La sélection du bouton d'ajout de
 *       la barre d'action démarre un nouvelle activité (AddActivity dont
 *       l'unité fonctionnelle est un AddFragment) dans laquelle on pourra
 *       ajouter un utilisateur au serveur.
 * 
 *       Dans la vue tablette, l'affichage est scindé entre cette liste et les
 *       fragments d'ajout ou d'édition d'utilisateur. Le fragment d'édition est
 *       affiché quand un élément est sélectionné dans la liste. Il permet de
 *       modifier ou supprimer l'utilisateur sélectionné du serveur. Le fragment
 *       d'ajout est affiché quand le bouton d'ajout de la barre d'action est
 *       sélectionné. Il permet d'ajouter un utilisateur sur le serveur.
 * 
 * @notes -
 ******************************************************************************/
public class MainActivity extends Activity implements
		UserListFragment.OnItemClickListener {

	/***************************************************************************
	 * Chaîne de caractères contenant l'adresse du serveur utilisé pour
	 * l'application
	 **************************************************************************/
	public static final String SERVER_URL = "http://chron-stats.herokuapp.com/";

	/***************************************************************************
	 * Chaîne de caractère contenant l'extension de fichier JSON
	 **************************************************************************/
	public static final String JSON_EXTENSION = ".json";

	// Le fragment d'ajout de cette activité
	private AddFragment addFragment = null;

	// Le fragment d'édition de cette activité
	private EditFragment editFragment = null;

	/***************************************************************************
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 **************************************************************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/***************************************************************************
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 **************************************************************************/
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

	/***************************************************************************
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 **************************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		UserListFragment userListFragment = (UserListFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_userList);
		userListFragment.setListener(this);
	}

	/***************************************************************************
	 * @see android.app.Activity#onResume()
	 **************************************************************************/
	@Override
	protected void onResume() {
		super.onResume();
		refreshList();
	}

	/***************************************************************************
	 * Used to refresh UserListFragment in onResume and from add and edit
	 * fragments
	 **************************************************************************/
	public void refreshList() {
		UserListFragment plf = (UserListFragment) getFragmentManager()
				.findFragmentById(R.id.fragment_userList);
		plf.refreshList();
	}

	/***************************************************************************
	 * Used to remove editFragment from tablet view after delete action
	 **************************************************************************/
	public void discardEditFragment() {
		FragmentManager fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();

		ft.remove(editFragment);
		ft.commit();
		editFragment = null;
	}

	/***************************************************************************
	 * Used to remove addFragment from tablet view after add action
	 **************************************************************************/
	public void discardAddFragment() {
		FragmentManager fm = getFragmentManager();

		FragmentTransaction ft = fm.beginTransaction();

		ft.remove(addFragment);
		ft.commit();
		addFragment = null;
	}

	/***************************************************************************
	 * Le callback que le fragment de liste d'utilisateurs appelle quand un
	 * élément est cliqué, selon le comportement suivant:
	 * <ul>
	 * <li>Si on trouve un frameLayout, on se trouve en mobile view:
	 * <ul>
	 * <li>on démarre l'activité d'édition d'utilisateur</li>
	 * </ul>
	 * </li>
	 * <li>Sinon, c'est qu'on se trouve en tablet view:
	 * <ul>
	 * <li>Si un EditFragment existe déjà, on le réutilise en mettant à jour son
	 * contenu.</li>
	 * <li>Si un AddFragment existe déjà, on le remplace par un nouveau
	 * EditFragment</li>
	 * <li>Sinon on crée tout simplement un nouveau EditFragment</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param user
	 *            L'utilisateur à passer à l'action d'édition
	 * 
	 * @see com.chron_stats_android.UserListFragment.OnItemClickListener#onItemClick(com.chron_stats_android.model.User)
	 **************************************************************************/
	@Override
	public void onItemClick(User user) {
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout_addEditFragment);
		FragmentManager fm = getFragmentManager();
		editFragment = (EditFragment) fm.findFragmentById(R.id.fragment_edit);
		addFragment = (AddFragment) fm.findFragmentById(R.id.fragment_add);
		if (frameLayout == null) {
			/*
			 * FrameLayout ne se trouve pas dans le layout ou que l'orientation
			 * est verticale, il faut alors démarrer l'activité EditActivity en
			 * lui passant l'utilisateur à éditer
			 */
			Intent intent = new Intent(this, EditActivity.class);
			intent.putExtra("user", user);
			startActivity(intent);
		} else if (editFragment != null) {
			/*
			 * EditFragment est dans le layout, il faut alors lui dire de mettre
			 * à jour sa vue.
			 */
			editFragment.updateContent(user);
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
		} else {
			/*
			 * Rien ne se trouve dans le layout, on crée donc un nouveau
			 * AddFragment
			 */
			FragmentTransaction ft = fm.beginTransaction();
			editFragment = new EditFragment();
			Bundle args = new Bundle();
			args.putSerializable("user", user);
			editFragment.setArguments(args);
			ft.replace(R.id.frameLayout_addEditFragment, editFragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}

	/***************************************************************************
	 * La fonction utilisée quand le bouton d'ajout de la barre d'action est
	 * clické
	 * <ul>
	 * <li>Si on trouve un frameLayout, on se trouve en mobile view:
	 * <ul>
	 * <li>on démarre l'activité d'ajout d'utilisateurs</li>
	 * </ul>
	 * </li>
	 * <li>Sinon, c'est qu'on se trouve en tablet view:
	 * <ul>
	 * <li>Si un AddFragment existe déjà, on le réutilise en effaçant son
	 * contenu.</li>
	 * <li>Si un EditFragment existe déjà, on le remplace par un nouveau
	 * AddFragment</li>
	 * <li>Sinon on crée tout simplement un nouveau AddFragment</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @see com.chron_stats_android.UserListFragment.OnItemClickListener#onItemClick(com.chron_stats_android.model.User)
	 **************************************************************************/
	public void addAction() {
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayout_addEditFragment);
		FragmentManager fm = getFragmentManager();
		editFragment = (EditFragment) fm.findFragmentById(R.id.fragment_edit);
		addFragment = (AddFragment) fm.findFragmentById(R.id.fragment_add);
		if (frameLayout == null) {
			/*
			 * EditFragment ne se trouve pas dans le layout ou que l'orientation
			 * est verticale, il faut alors démarrer l'activité EditActivity en
			 * lui passant l'utilisateur à éditer
			 */
			Intent intent = new Intent(this, AddActivity.class);
			startActivity(intent);
		} else if (addFragment != null) {
			/*
			 * AddFragment est dans le layout, il faut alors effacer son
			 * contenu.
			 */
			addFragment.clearContent();
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
		} else {
			/*
			 * Rien ne se trouve dans le layout, on crée donc un nouveau
			 * AddFragment
			 */
			FragmentTransaction ft = fm.beginTransaction();
			addFragment = new AddFragment();

			ft.replace(R.id.frameLayout_addEditFragment, addFragment);
			ft.addToBackStack(null);
			ft.commit();
		}
	}
}
