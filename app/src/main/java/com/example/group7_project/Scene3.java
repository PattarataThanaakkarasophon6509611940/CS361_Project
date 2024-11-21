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

public class Scene3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        // ตัวนับสถานะของ Scene 3
        final int[] sceneIndex = {1};

        // รับค่าสีจาก Action2
        String color = getIntent().getStringExtra("color");

        // แสดง Scene 3_1 ทันทีที่เปิด
        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_white);
        }

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    if ("black".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_2_black);
                        text.setText(R.string.scene_3_2);
                    } else if ("orange".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_2_orange);
                        text.setText(R.string.scene_3_2);
                    } else if ("white".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_2_white);
                        text.setText(R.string.scene_3_2);
                    }
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    if ("black".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_3_black);
                        text.setText(R.string.scene_3_3);
                    } else if ("orange".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_3_orange);
                        text.setText(R.string.scene_3_3);
                    } else if ("white".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_3_3_white);
                        text.setText(R.string.scene_3_3);
                    }
                    sceneIndex[0] = 3;
                    break;
                default:
                    // เมื่อจบ Scene3 ให้ไปที่ Action3
                    Intent intent = new Intent(Scene3.this, Action3.class);
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
            }
        });

    }

}
