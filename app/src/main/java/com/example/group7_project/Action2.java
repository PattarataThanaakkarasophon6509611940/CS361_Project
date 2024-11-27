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

public class Action2 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
    private long backPressedTime =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        dbHelper = new DatabaseHelper(this);
        setting = new Setting(this);
        setContentView(R.layout.action_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        color = getIntent().getStringExtra("color");

        ImageView imgScene = findViewById(R.id.scene);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Action2",sceneIndex,null,color);
        });

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_white);
        }

        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);

        btnNo.setOnClickListener(v -> {
            Intent intent = new Intent(Action2.this, EndingBad.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action2.this, Scene3.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Action2", sceneIndex,null,color);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Action2")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Action2", sceneIndex, null, color); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
