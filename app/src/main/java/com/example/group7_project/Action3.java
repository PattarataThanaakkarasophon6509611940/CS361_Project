package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Action3 extends AppCompatActivity {
    private int sceneIndex = 1;
    private String color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        color = getIntent().getStringExtra("color");

        Button btnPass = findViewById(R.id.btnPass);
        Button btnFail = findViewById(R.id.btnFail);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        btnPass.setOnClickListener(v -> {
            Intent intent = new Intent(Action3.this, Scene3.class);
            intent.putExtra("color", color);
            intent.putExtra("status", "pass");
            startActivity(intent);
            finish();
        });

        btnFail.setOnClickListener(v -> {
            Intent intent = new Intent(Action3.this, Scene3.class);
            intent.putExtra("color", color);
            intent.putExtra("status", "fail");
            startActivity(intent);
            finish();
        });
    }
}
