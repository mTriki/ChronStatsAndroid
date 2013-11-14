package com.chron_stats_android.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

/*******************************************************************************
 * GetJSONTask.java
 * 
 * @author Crescenzio Fabio
 * @author Fresco Alain
 * @author Therisod Romain
 * @author Triki Mohamed
 * @author Walpen Laurian
 * 
 * @goal Tache de requête de JSON depuis une URL.
 * 
 *       Télécharge un fichier JSON présent sur une certaine URL et le retourne
 *       une chaîne de caractères contenant le JSON désiré en résultat pour
 *       traitement.
 * 
 *       Utilisé pour peupler la liste d'utilisateur dans UserListFragment,
 *       ainsi que dans le fragment d'édition pour récupérer et afficher les
 *       informations d'un utilisateur.
 * 
 * @notes -
 ******************************************************************************/
public class GetJSONTask extends AsyncTask<URL, Void, String> {

	// Listener du callback pour le traitement ultérieur du JSON.
	private CallBackListener cbl;

	/***************************************************************************
	 * L'interface servant à définir le callback appelé après l'obtention du
	 * JSON désiré.
	 **************************************************************************/
	public interface CallBackListener {
		/***********************************************************************
		 * Méthode callback à implémenter. Appelée une fois le JSON récupéré.
		 * 
		 * @param json
		 *            La chaîne de caractère contenant le JSON.
		 **********************************************************************/
		public void callback(String json);
	}

	/***************************************************************************
	 * Enregistre le listener à l'interface CallBackListener.
	 * 
	 * @param listener
	 *            La classe implémentant CallBackListener.
	 **************************************************************************/
	public void setListener(CallBackListener cbl) {
		this.cbl = cbl;
	}

	/***************************************************************************
	 * Méthode de la classe AsyncTask se chargeant de traiter une tâche de fond.
	 * Redéfinie ici pour récupérer un JSON depuis une URL.
	 * 
	 * @param urls
	 *            Les urls à traiter. Pour le prototype, seulement la première
	 *            est traitée.
	 * 
	 * @return La chaîne de caractères qui contient le JSON. Sera passée à la
	 *         méthode onPostExecute() pour traitement.
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 **************************************************************************/
	@Override
	protected String doInBackground(URL... urls) {
		String json = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					urls[0].openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);

			json = buffer.toString();
		} catch (Exception e) {
			Log.e("GetJSON", "GetJSONTask: " + e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e("GetJSON", "GetJSONTask: " + e.getMessage());
				}
			}
		}

		return json;
	}

	/***************************************************************************
	 * Méthode de la classe AsyncTask appelée après que doInBackground ait fini
	 * son traitement. Passe le JSON récupérer à la méthode callback implémentée
	 * par la classe appelante.
	 * 
	 * @param result
	 *            Le JSON à traiter (retourné par la méthode doInBackground()).
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 **************************************************************************/
	@Override
	protected void onPostExecute(String result) {
		cbl.callback(result);
	}
}
