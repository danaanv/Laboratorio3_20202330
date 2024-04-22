package com.example.laboratorio3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class Visualizar extends AppCompatActivity {

    int primoAImprimir;
    String primoAImprimirS;
    TextView primeNumbersTextView;
    private static final String TAG = "PrimeNumbersActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_visualizar);
        primeNumberPairs = new ArrayList<>();

        primeNumbersTextView = findViewById(R.id.principal);
        fetchPrimeNumbers();

        if (primeNumberPairs == null){
            primeNumbersTextView.setText("Error en la API");
        }

        Button buttonAscenderDescender = findViewById(R.id.buttonAscenderDescender);
        buttonAscenderDescender.setOnClickListener(v -> {
            if (buttonAscenderDescender.getText().toString().equals("Ascender")){
                buttonAscenderDescender.setText("Descender");
            } else if (buttonAscenderDescender.getText().toString().equals("Descender")) {
                buttonAscenderDescender.setText("Ascender");
            }
        });

        Button buttonPausarReiniciar = findViewById(R.id.buttonPausarReiniciar);
        buttonPausarReiniciar.setOnClickListener(v -> {
            if (buttonPausarReiniciar.getText().toString().equals("Pausar")){
                buttonPausarReiniciar.setText("Reiniciar");
            } else if (buttonPausarReiniciar.getText().toString().equals("Reiniciar")) {
                buttonPausarReiniciar.setText("Pausar");
            }
        });

        EditText ordenEscrito = findViewById(R.id.ordenEscrito);
        Button buttonBuscar = findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(v -> {
            String orden = ordenEscrito.getText().toString();
            primoAImprimir = BuscarValor(Integer.valueOf(orden));
            primoAImprimirS = String.valueOf(primoAImprimir);
            primeNumbersTextView.setText(primoAImprimirS);
        });

    }

    private int BuscarValor(Integer integer){
        int primo = 0;
        for (int i = 0; i < (primeNumberPairs != null ? primeNumberPairs.size() : 0); i += 2) {
            Log.d("Visualizar", "Valor de primeNumberPairs: " + primeNumberPairs.get(i));
            if (Objects.equals(integer, primeNumberPairs.get(i))) {
                primo = primeNumberPairs.get(i+1);
                break;
            }
        }
        return primo;
    }

    protected void onResume() {
        super.onResume();
        showToast("Estás en la pantalla Visualizar Números Primos");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Integer> primeNumberPairs;

    private void fetchPrimeNumbers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://prime-number-api.onrender.com/primeNumbers?len=999&order=1");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // Leer la respuesta de la API
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Procesar la respuesta JSON
                    JSONArray jsonArray = new JSONArray(response.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject primeInfo = jsonArray.getJSONObject(i);
                        int order = primeInfo.getInt("order");
                        int primeNumber = primeInfo.getInt("number");
                        primeNumberPairs.add(order);
                        primeNumberPairs.add(primeNumber);
                    }

                } catch (IOException | JSONException e) {
                    Log.e(TAG, "Error fetching or parsing prime numbers", e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            primeNumbersTextView.setText("Error: Unable to fetch prime numbers.");
                        }
                    });
                }
            }
        }).start();
    }
}