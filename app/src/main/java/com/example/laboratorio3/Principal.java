package com.example.laboratorio3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Principal extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        Button buttonVisualizar = findViewById(R.id.buttonVisualizar);
        buttonVisualizar.setOnClickListener(v->{
            Intent intent = new Intent(Principal.this,Visualizar.class);
            startActivity(intent);
        });

        editText = findViewById(R.id.editTextID);

    }

    public void onBuscarClick(View view) {
        String searchText = editText.getText().toString().trim();

        if (!searchText.isEmpty()) {
            Intent intent = new Intent(this, Buscador.class);
            intent.putExtra("searchText", searchText);
            startActivity(intent);
        }
    }
}