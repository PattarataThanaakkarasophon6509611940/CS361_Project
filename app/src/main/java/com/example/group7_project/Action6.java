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

public class Action6 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String color = getIntent().getStringExtra("color");
        String book = getIntent().getStringExtra("book");

        Button btnPass = findViewById(R.id.btnPass);
        Button btnFail = findViewById(R.id.btnFail);

        btnPass.setOnClickListener(v -> {
            Intent intent = new Intent(Action6.this, Scene5.class);
            intent.putExtra("color", color);
            intent.putExtra("book", book);
            startActivity(intent);
            finish();
        });

        btnFail.setOnClickListener(v -> {
            Intent intent = new Intent(Action6.this, Scene4.class);
            intent.putExtra("color", color);
            intent.putExtra("book", book);
            intent.putExtra("result", "overtime");
            startActivity(intent);
            finish();
        });
    }
}
