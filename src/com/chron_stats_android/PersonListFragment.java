package com.chron_stats_android;

import java.net.URL;
import java.util.ArrayList;

import com.chron_stat_android.R;
import com.chron_stats_android.model.Person;
import com.chron_stats_android.model.User;
import com.chron_stats_android.tasks.GetJSONTask;
import com.google.gson.Gson;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PersonListFragment extends ListFragment implements GetJSONTask.CallBackListener {
	private PersonAdapter adapter;
	public OnItemClickListener interfaceItemClickListener;
	private static Gson gson;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		gson = new Gson();

		adapter = new PersonAdapter(getActivity());

		URL personsURL = null;
		try {
			personsURL = new URL(MainActivity.SERVER_URL+"users"+MainActivity.JSON_EXTENSION);
			Log.d("DEBUG - PersonListFragment", "URL: "+personsURL);
			GetJSONTask task = new GetJSONTask();
			task.setListener(this);
			task.execute(personsURL);
		} catch (Exception e) {
			Log.e("GetJSON", "GetJSONTask: "+e.getMessage());
		}
	}
	
	public void refreshList() {
		URL personsURL = null;
		try {
			personsURL = new URL(MainActivity.SERVER_URL+"users"+MainActivity.JSON_EXTENSION);
			Log.d("DEBUG - PersonListFragment", "URL: "+personsURL);
			GetJSONTask task = new GetJSONTask();
			task.setListener(this);
			task.execute(personsURL);
		} catch (Exception e) {
			Log.e("GetJSON", "GetJSONTask: "+e.getMessage());
		}
	}

	/**
	 * L'interface pour définir le callback pour la sélection d'élément
	 */
	public interface OnItemClickListener {
		void onItemClick(Person person);
	}
	
	public void setListener (OnItemClickListener listener) {
		this.interfaceItemClickListener = listener;
	}

	@Override
	public void callback(String json) {
		populateList(json);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		/**
		 * Appelle l'implementation de la méthode onListFragmentItemClick
		 * dans l'activité contenant ce fragment */
		interfaceItemClickListener.onItemClick(adapter.getItem(position));
	}

	public void populateList(Person[] persons) {
		for (int i = 0; i < persons.length; i++) {
			Log.d("DEBUG - adding persons", ((User)persons[i]).toString());
			adapter.addItem(persons[i]);
		}

		// Lier au nouvel adapter
		setListAdapter(adapter);
	}

	public void populateList(String personsJSON) {
		Person[] persons = JSONToPersonArray(personsJSON);
		populateList(persons);
	}

	// TODO: generic method for subclasses of Person
	public static Person JSONToPerson(String personJSON) {
		return gson.fromJson(personJSON, User.class);
	}

	// TODO: generic method for subclasses of Person
	public static Person[] JSONToPersonArray(String personsJSON) {
		return gson.fromJson(personsJSON, User[].class);
	}

	protected class PersonAdapter extends BaseAdapter {
		private static final int TYPE_USER = 0;
		private static final int TYPE_COUNT = 1;
		private ArrayList<Person> list = new ArrayList<Person>();
		private LayoutInflater inflater;

		public PersonAdapter(Context context) {
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(Person person) {
			list.add(person);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Person getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			int type = -1;
			Person person = list.get(position);

			if (person instanceof User) {
				type = 0;
			}

			return type;
		}

		public int getViewTypeCount() {
			return TYPE_COUNT;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int type = getItemViewType(position);
			switch(type) {
			case TYPE_USER:
				// User edition logic
				convertView = inflater.inflate(R.layout.list_item_person, null);
				String name = ((User)list.get(position)).getName();
				((TextView)convertView.findViewById(R.id.textView_personItem)).setText(name);
			default:
				break;
			}

			return convertView;
		}
	}
}
