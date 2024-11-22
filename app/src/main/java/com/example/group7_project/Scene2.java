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

public class Scene2 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // รับค่าสีจาก Action1
        String color = getIntent().getStringExtra("color");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        // ใช้ค่าสีเพื่อเลือกแสดง Scene 2 ที่เหมาะสม
        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_black);
            text.setText(R.string.scene_2_2_1);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_orange);
            text.setText(R.string.scene_2_2_1);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_white);
            text.setText(R.string.scene_2_2_1);
        }

        // ตั้งค่า OnClickListener สำหรับ Scene 2
        btnNext.setOnClickListener(v -> {
            text.setText(R.string.scene_2_2_2);

            // Intent ไปยัง Action2 เมื่อ Scene 2 จบ
            btnNext.setOnClickListener(v2 -> {
                Intent intent = new Intent(Scene2.this, Action2.class);
                intent.putExtra("color", color); // ส่งค่า color ไปยัง Action2
                startActivity(intent); // เปิดหน้า Action2
            });
        });

    }

}
