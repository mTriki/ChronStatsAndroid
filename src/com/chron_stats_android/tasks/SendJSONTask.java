package com.chron_stats_android.tasks;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;

public class SendJSONTask extends AsyncTask<Request, Void, Void> {
	private CallBackListener cbl = null;
	private int editNumber = 0;
	private int createNumber = 0;
	private int deleteNumber = 0;

	public interface CallBackListener {
		public void callback(String message);
	}

	public void setListener(CallBackListener cbl) {
		this.cbl = cbl;
	}

	@Override
	protected Void doInBackground(Request... requests) {
		for (int i = 0; i < requests.length; i++) {
			try {
				HttpClient client = new DefaultHttpClient();
				HttpRequestBase request = null;
				try {
					if (requests[i].getMethod().equals("PUT")) {
						request = new HttpPut(requests[i].getURI());
						((HttpPut) request).setEntity(new ByteArrayEntity(
								requests[i].getPersonJSON().getBytes("UTF-8")));
						editNumber++;
					} else if (requests[i].getMethod().equals("POST")) {
						request = new HttpPost(requests[i].getURI());
						((HttpPost) request).setEntity(new ByteArrayEntity(
								requests[i].getPersonJSON().getBytes("UTF-8")));
						createNumber++;
					} else if (requests[i].getMethod().equals("DELETE")) {
						request = new HttpDelete(requests[i].getURI());
						deleteNumber++;
					}
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				request.addHeader("Accept", "application/json");
				request.addHeader("Content-Type", "application/json");
				client.execute(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		String message = "";
		if (createNumber == 1)
			message += "Utilisateur créé\n";
		else if (createNumber != 0)
			message += createNumber+" utilisateurs créés\n";
		if (editNumber == 1)
			message += "Utilisateur modifié\n";
		else if (editNumber != 0)
			message += editNumber+" utilisateurs modifiés\n";
		if (deleteNumber == 1)
			message += "Utilisateur supprimé\n";
		else if (deleteNumber != 0)
			message += deleteNumber+" utilisateurs supprimés\n";
		cbl.callback(message.trim());
	}
}
