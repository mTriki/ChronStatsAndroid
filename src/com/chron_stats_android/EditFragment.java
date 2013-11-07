package com.chron_stats_android;

import com.chron_stat_android.R;
import com.chron_stats_android.model.Person;
import com.chron_stats_android.model.User;
import com.chron_stats_android.tasks.Request;
import com.chron_stats_android.tasks.SendJSONTask;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditFragment extends Fragment implements
		SendJSONTask.CallBackListener {
	// TODO: Generic fragment for every type of person
	private Person editPerson;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_edit, container, false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((Button) getActivity().findViewById(R.id.button_edit))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						editPerson();
					}
				});

		((Button) getActivity().findViewById(R.id.button_delete))
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						deletePerson();
					}
				});
	}

	public void editPerson() {
		User user = (User) editPerson;
		String url = MainActivity.SERVER_URL + "users/" + editPerson.getID();
		String method = "PUT";
		String newName = ((EditText) getActivity().findViewById(
				R.id.editText_name)).getText().toString();
		String newEmail = ((EditText) getActivity().findViewById(
				R.id.editText_email)).getText().toString();
		user.setName(newName);
		user.setEmail(newEmail);
		SendJSONTask task = new SendJSONTask();
		task.setListener(this);
		task.execute(new Request(url, method, user, User.class));
	}

	public void deletePerson() {
		User user = (User) editPerson;
		String url = MainActivity.SERVER_URL + "users/" + editPerson.getID();
		String method = "DELETE";
		String newName = ((EditText) getActivity().findViewById(
				R.id.editText_name)).getText().toString();
		String newEmail = ((EditText) getActivity().findViewById(
				R.id.editText_email)).getText().toString();
		user.setName(newName);
		user.setEmail(newEmail);
		SendJSONTask task = new SendJSONTask();
		task.setListener(this);
		task.execute(new Request(url, method, user, User.class));
	}

	public void updateContent(Person person) {
		this.editPerson = person;
		User user = (User) editPerson;

		// not generic
		((EditText) getActivity().findViewById(R.id.editText_name))
				.setEnabled(true);
		((EditText) getActivity().findViewById(R.id.editText_email))
				.setEnabled(true);
		((EditText) getActivity().findViewById(R.id.editText_name))
				.setText(user.getName());
		((EditText) getActivity().findViewById(R.id.editText_email))
				.setText(user.getEmail());
	}

	@Override
	public void callback(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}
