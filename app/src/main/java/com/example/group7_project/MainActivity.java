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
            Cursor cursor = dbHelper.getLastSubscene();

            if (cursor != null && cursor.moveToFirst()) {
                String scene = cursor.getString(0);
                int subscene = cursor.getInt(1);
                cursor.close();

                System.out.println(scene);
                System.out.println(subscene);

                Intent intent = getSceneIntent(scene); // ใช้ฟังก์ชันเพื่อหา Intent ที่เหมาะสม
                if (intent != null) {
                    intent.putExtra("subscene", subscene); // ส่งค่า subscene ไปยัง Scene
                    startActivity(intent);
                }
            } else {
                assert cursor != null;
                cursor.close();
                Toast.makeText(this, R.string.noProgress, Toast.LENGTH_SHORT).show();
            }


        });
        btnNewGame.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Scene1.class);
            intent.putExtra("subscene", 1);
            startActivity(intent);
        });
    }
    private Intent getSceneIntent(String scene) {
        try {
            switch (scene) {
                case "Scene1":
                    return new Intent(MainActivity.this, Scene1.class);
                case "Scene2":
                    return new Intent(MainActivity.this, Scene2.class);
                case "Scene3":
                    return new Intent(MainActivity.this, Scene3.class);
                case "Scene4":
                    return new Intent(MainActivity.this, Scene4.class);
                case "Scene5":
                    return new Intent(MainActivity.this, Scene5.class);
                case "Action1":
                    return new Intent(MainActivity.this, Action1.class);
                case "Action2":
                    return new Intent(MainActivity.this, Action2.class);
                case "Action3":
                    return new Intent(MainActivity.this, Action3.class);
                case "Action4":
                    return new Intent(MainActivity.this, Action4.class);
                case "Action5":
                    return new Intent(MainActivity.this, Action5.class);
                case "Action6":
                    return new Intent(MainActivity.this, Action6.class);
                case "Action7":
                    return new Intent(MainActivity.this, Action7.class);
                default:
                    throw new IllegalArgumentException("Unknown scene: " + scene);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading saved progress.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }
}