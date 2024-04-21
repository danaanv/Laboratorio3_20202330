package com.example.laboratorio3;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MovieApiTask extends AsyncTask<Void, Void, String> {

    private static final String API_KEY = "bf81d461";
    private static final String BASE_URL = "https://www.omdbapi.com/";

    private MovieApiListener listener;

    private String imdbId;

    public interface MovieApiListener {
        void onMovieDetailsReceived(JSONObject movieDetails);
    }

    public MovieApiTask(String imdbId, MovieApiListener listener) {
        this.imdbId = imdbId;
        this.listener = listener;
    }
    protected String doInBackground(Void... voids) {
        String apiUrl = BASE_URL + "?apikey=" + API_KEY + "&i=" + imdbId;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            Log.e("MovieApiTask", "Error fetching data", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                listener.onMovieDetailsReceived(jsonObject);
            } catch (JSONException e) {
                Log.e("MovieApiTask", "Error parsing JSON", e);
            }
        }
    }
}

