package com.example.laboratorio3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resultados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultados);

        Button buttonRegresarResultados = findViewById(R.id.buttonRegresarResultados);
        buttonRegresarResultados.setOnClickListener(v -> {
            Intent intent = new Intent(Resultados.this, Principal.class);
            startActivity(intent);
        });
    }

    protected void onResume() {
        super.onResume();
        showToast("Estás en la pantalla Resultados No Encontrados");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}