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

public class Action2 extends AppCompatActivity {
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
        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_white);
        }

        // ค้นหา View
        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);

        // กดปุ่ม "No" เพื่อไปยังหน้า scene_bad_ending.xml
        btnNo.setOnClickListener(v -> {
            Intent intent = new Intent(Action2.this, BadEnding.class);
            startActivity(intent); // เปิดหน้า Scene Bad Ending
            finish(); // ปิด Action2
        });

        // กดปุ่ม "Yes" เพื่อไปยัง Scene 3 พร้อมส่งค่า color กลับไปยัง GameActivity
        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action2.this, Scene3.class);
            intent.putExtra("color", color);
            startActivity(intent); // เปิดหน้า Action2
        });

    }

}
