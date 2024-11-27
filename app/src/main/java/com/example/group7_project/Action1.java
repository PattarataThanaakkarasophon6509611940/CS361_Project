package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Action1 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    private long backPressedTime =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_1);
        dbHelper = new DatabaseHelper(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final Button btnBlack = findViewById(R.id.btnBlack);
        final Button btnOrange = findViewById(R.id.btnOrange);
        final Button btnWhite = findViewById(R.id.btnWhite);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        btnBlack.setOnClickListener(v -> sendColor("black"));
        btnOrange.setOnClickListener(v -> sendColor("orange"));
        btnWhite.setOnClickListener(v -> sendColor("white"));

        setting = new Setting(this);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Action1",sceneIndex,null,null);
        });
    }
    private void sendColor(String color) {
        Intent intent = new Intent(Action1.this, Scene2.class);
        intent.putExtra("color", color);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Action1", sceneIndex,null,null);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Action1")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Action1", sceneIndex, null, null); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}

