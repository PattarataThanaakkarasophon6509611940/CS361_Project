package com.example.group7_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final Button btnNewGame = findViewById(R.id.btnNewGame);
        final Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            Cursor cursor = dbHelper.getLastProgress();

            if (cursor != null && cursor.moveToFirst()) {
                String scene = cursor.getString(cursor.getColumnIndexOrThrow("scene"));
                int subscene = cursor.getInt(cursor.getColumnIndexOrThrow("subscene"));
                cursor.close();

                Intent intent;
                switch (scene) {
                    case "Scene1":
                        intent = new Intent(MainActivity.this, Scene1.class);
                        break;
                    case "Scene2":
                        intent = new Intent(MainActivity.this, Scene2.class);
                        break;
                    case "Scene3":
                        intent = new Intent(MainActivity.this, Scene3.class);
                        break;
                    case "Scene4":
                        intent = new Intent(MainActivity.this, Scene1.class);
                        break;
                    case "Scene5":
                        intent = new Intent(MainActivity.this, Scene2.class);
                        break;
                    default:
                        intent = null;
                        break;
                }
                if (intent != null) {
                    intent.putExtra("subscene", subscene);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, R.string.noProgress, Toast.LENGTH_SHORT).show();
            }
        });
        btnNewGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Scene1.class);
            intent.putExtra("subscene", 1);
            startActivity(intent);
        });
    }
}