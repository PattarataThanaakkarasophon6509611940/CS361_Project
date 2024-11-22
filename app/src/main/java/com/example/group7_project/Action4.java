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

public class Action4 extends AppCompatActivity {
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

        // ค้นหา View
        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_white);
        }

        text.setText(R.string.action_4);
        btnYes.setText(R.string.action_4_choice_1);
        btnNo.setText(R.string.action_4_choice_2);

        btnNo.setOnClickListener(v -> {
            Intent intent = new Intent(Action4.this, Scene4.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action4.this,Ending.class);
            intent.putExtra("endingType", "happy");
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });
    }
}
