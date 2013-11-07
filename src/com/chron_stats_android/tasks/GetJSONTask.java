package com.chron_stats_android.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class GetJSONTask extends AsyncTask<URL, Void, String>  {
	private CallBackListener cbl;
	
	public interface CallBackListener{
		public void callback(String json);
	}
	
	public void setListener(CallBackListener cbl) {
		this.cbl = cbl;
	}
	
	@Override
	protected String doInBackground(URL... urls) {
		String json = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(urls[0].openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			json = buffer.toString();
		} catch (Exception e) {
			Log.e("GetJSON", "GetJSONTask: "+e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e("GetJSON", "GetJSONTask: "+e.getMessage());
				}
			}	
		}

		return json;
	}
	
	@Override
	protected void onPostExecute(String result) {
		cbl.callback(result);
	}
}
