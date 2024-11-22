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

public class Action7  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // รับค่า color จาก Intent
        String color = getIntent().getStringExtra("color");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);

        imgScene.setImageResource(R.drawable.scene_5_3_1);
        text.setText(R.string.action_7);
        btnNo.setText(R.string.action_7_choice_1);
        btnYes.setText(R.string.action_7_choice_2);

        btnNo.setOnClickListener(v -> {
            Intent intent = new Intent(Action7.this, Ending.class);
            intent.putExtra("color", color);
            intent.putExtra("endingType", "sad");
            startActivity(intent);
            finish();
        });

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action7.this, Ending.class);
            intent.putExtra("color", color);
            intent.putExtra("endingType", "true");
            startActivity(intent);
        });

    }
}
