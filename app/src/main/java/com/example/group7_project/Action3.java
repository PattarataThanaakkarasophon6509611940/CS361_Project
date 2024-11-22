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

        Button btnPass = findViewById(R.id.btnPass);
        Button btnFail = findViewById(R.id.btnFail);

        btnPass.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("color", color);
            resultIntent.putExtra("status", "pass");
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnFail.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("color", color);
            resultIntent.putExtra("status", "fail");
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }

}
