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

public class Action4 extends AppCompatActivity {
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

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        color = getIntent().getStringExtra("color");
        int subscene = getIntent().getIntExtra("subscene", 1);

        sceneIndex = subscene;

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Action4",sceneIndex,null,color);
        });

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
            Intent intent = new Intent(Action4.this,EndingHappy.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Action4", sceneIndex, null, color);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Action4")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Action4", sceneIndex, null, color); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
