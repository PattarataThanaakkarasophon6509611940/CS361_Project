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
        setContentView(R.layout.action_3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String color = getIntent().getStringExtra("color");
        String book = getIntent().getStringExtra("book");

        Button btnPass = findViewById(R.id.btnPass);
        Button btnFail = findViewById(R.id.btnFail);

        ImageView imgScene = findViewById(R.id.scene);

        btnPass.setOnClickListener(v -> {
            Intent intent = new Intent(Action6.this, Scene5.class);
            intent.putExtra("color", color);
            intent.putExtra("book", book);
            intent.putExtra("status", "pass");
            startActivity(intent);
            finish();
        });

        btnFail.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("color", color);
            resultIntent.putExtra("book", book);
            resultIntent.putExtra("status", "fail");
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
