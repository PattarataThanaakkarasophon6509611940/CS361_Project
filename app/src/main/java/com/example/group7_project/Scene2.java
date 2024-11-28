package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Scene2 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    private String color;
    private long backPressedTime =0;
    private String book;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_1);
        dbHelper = new DatabaseHelper(this);
        setting = new Setting(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // รับค่าจาก Intent
        int subscene = getIntent().getIntExtra("subscene", 1);
        color = getIntent().getStringExtra("color");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        // ตั้งค่า sceneIndex ให้ตรงกับ subscene
        sceneIndex = subscene;

        // โหลด subscene ที่ระบุ
        loadSubscene(sceneIndex, imgScene, text, color);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene2",sceneIndex,null,color);
        });

        btnNext.setOnClickListener(v -> {
            sceneIndex++;
            if (sceneIndex > 2) {
                Intent intent = new Intent(Scene2.this, Action2.class);
                intent.putExtra("color", color);
                startActivity(intent);
                finish();
            } else {
                loadSubscene(sceneIndex, imgScene, text, color);
            }
        });

    }
    private void loadSubscene(int sceneIndex, ImageView imgScene, TextView text, String color) {
        switch (sceneIndex) {
            case 1:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_white);
                }
                text.setText(R.string.scene_2_2_1);
                break;
            case 2:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_2_2_white);
                }
                text.setText(R.string.scene_2_2_2);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null); // แสดงภาพว่างกรณีไม่มี subscene
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveSceneState();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            saveSceneState();
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        saveSceneState();
    }
    private void saveSceneState() {
        dbHelper.saveLastSubscene("Scene2", sceneIndex, book, color);
        dbHelper.close();
    }
}