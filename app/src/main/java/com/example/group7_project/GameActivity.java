package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_1to4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startScene1();

    }//end onCreate

    // Scene 1: จัดการการแสดงผลและการเปลี่ยน Scene
    private void startScene1() {
        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);

        // ตัวนับสถานะของ Scene 1
        final int[] sceneIndex = {1};

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    imgScene.setImageResource(R.drawable.scene_1_2);
                    text.setText(R.string.scene_1_2);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    imgScene.setImageResource(R.drawable.scene_1_3);
                    text.setText(R.string.scene_1_3);
                    sceneIndex[0] = 3;
                    break;
                case 3:
                    imgScene.setImageResource(R.drawable.scene_1_4);
                    text.setText(R.string.scene_1_4);
                    sceneIndex[0] = 4;
                    break;
                default:
                    // เมื่อจบ Scene 1 ให้ไปที่ Action1
                    Intent intent = new Intent(GameActivity.this, Action1.class);
                    startActivityForResult(intent, 1); // ใช้ requestCode = 1
                    break;
            }
        });
    }

    // รับผลลัพธ์จาก Action1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // รับค่าสีจาก Action1
            String selectedColor = data.getStringExtra("selectedColor");

            // เริ่ม Scene 2
            startScene2(selectedColor);
        }
    }

    // Scene 2: ใช้ค่าสีที่เลือก
    private void startScene2(String color) {
        setContentView(R.layout.scene_2); // เปลี่ยน Layout เป็น Scene 2

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
        });
        // Intent ไปยัง Action2 เมื่อ Scene 2 จบ
        Intent intent = new Intent(GameActivity.this, Action2.class);
        intent.putExtra("selectedColor", color); // ส่งค่า color
        startActivity(intent); // เปิดหน้า Action2

    }
}
