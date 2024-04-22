package com.example.laboratorio3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Buscador extends AppCompatActivity implements MovieApiTask.MovieApiListener {

    TextView textViewTitle;
    TextView textViewDirector;
    TextView textViewActor;
    TextView textViewfechaDeEstreno;
    TextView textViewgenero;
    TextView textViewescritores;
    TextView textViewTrama;
    TextView text1;

    TextView text2;
    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscador);

        String imdbId = getIntent().getStringExtra("searchText");
        MovieApiTask movieApiTask = new MovieApiTask(imdbId, this,this);
        movieApiTask.execute();

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDirector = findViewById(R.id.textViewDirector);
        textViewActor = findViewById(R.id.textViewActor);
        textViewfechaDeEstreno = findViewById(R.id.textViewfechaDeEstreno);
        textViewgenero = findViewById(R.id.textViewgenero);
        textViewescritores = findViewById(R.id.textViewescritores);
        textViewTrama = findViewById(R.id.textViewTrama);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text22);
        text3 = findViewById(R.id.text3);

        CheckBox checkBox = findViewById(R.id.checkBox);
        Button buttonRegresar = findViewById(R.id.buttonRegresar);
        checkBox.setOnClickListener(v->{
            boolean isChecked = checkBox.isChecked();
            buttonRegresar.setEnabled(isChecked);
        });

        buttonRegresar.setOnClickListener(v->{
            Intent intent = new Intent(Buscador.this, Principal.class);
            startActivity(intent);
        });
    }

    protected void onResume() {
        super.onResume();
        showToast("Estás en la pantalla Buscador");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onMovieDetailsReceived(JSONObject movieDetails) {
        if (movieDetails != null) {
            try {
                String response = movieDetails.getString("Response");
                if (response.equals("False")){
                    Intent intent1 = new Intent(Buscador.this, Resultados.class);
                    startActivity(intent1);
                }else {
                    String title = movieDetails.getString("Title");
                    String director = movieDetails.getString("Director");
                    String actor = movieDetails.getString("Actors");
                    String fechaDeEstreno = movieDetails.getString("Released");
                    String genero = movieDetails.getString("Genre");
                    String escritores = movieDetails.getString("Writer");
                    String trama = movieDetails.getString("Plot");

                    textViewTitle.setText(title);
                    textViewDirector.setText(director);
                    textViewActor.setText(actor);
                    textViewfechaDeEstreno.setText(fechaDeEstreno);
                    textViewgenero.setText(genero);
                    textViewescritores.setText(escritores);
                    textViewTrama.setText(trama);

                    try {
                        JSONArray ratingsArray = movieDetails.getJSONArray("Ratings");

                        for (int i = 0; i < ratingsArray.length(); i++) {
                            JSONObject ratingObject = ratingsArray.getJSONObject(i);
                            String source = ratingObject.getString("Source");
                            String value = ratingObject.getString("Value");
                            Log.d("MainActivity", "Valor de source: " + source);
                            Log.d("MainActivity", "Valor de value: " + value);

                            if (source.equals("Internet Movie Database")) {
                                if(value == null || value.equals("")){
                                    text1.setText("No encontrado");
                                }else {
                                    text1.setText(value);
                                }
                            } else if (source.equals("Rotten Tomatoes")) {
                                if(value == null || value.equals("")){
                                    text2.setText("No encontrado");
                                }else {
                                    text2.setText(value);
                                }
                            } else if (source.equals("Metacritic")) {
                                if(value == null || value.equals("")){
                                    text3.setText("No encontrado");
                                }else {
                                    text3.setText(value);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                Log.e("MainActivity", "Error parsing movie details JSON", e);
            }


        }
    }
}