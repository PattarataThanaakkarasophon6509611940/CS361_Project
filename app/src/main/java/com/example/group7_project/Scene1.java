package com.example.group7_project;

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
import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

public class Scene1 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    private long backPressedTime =0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        dbHelper = new DatabaseHelper(this);
        setting = new Setting(this);
        setContentView(R.layout.scene_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper.printLastSubscene(); // เรียกใช้เมธอดเพื่อพิมพ์ข้อมูล

        int subscene = getIntent().getIntExtra("subscene", 1);

        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        // ตั้งค่า sceneIndex ให้ตรงกับ subscene
        sceneIndex = subscene;

        // โหลด subscene ที่ระบุ
        loadSubscene(sceneIndex, imgScene, text);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene1",sceneIndex,null,null);
        });

        btnNext.setOnClickListener(v -> {
            sceneIndex++;
            if (sceneIndex > 4) {
                Intent intent = new Intent(Scene1.this, Action1.class);
                startActivity(intent);
                finish();
            } else {
                loadSubscene(sceneIndex, imgScene, text);
            }
        });

    }//end onCreate

    private void loadSubscene(int sceneIndex, ImageView imgScene, TextView text) {
        switch (sceneIndex) {
            case 1:
                imgScene.setImageResource(R.drawable.scene_1_1);
                text.setText(R.string.scene_1_1);
                break;
            case 2:
                imgScene.setImageResource(R.drawable.scene_1_2);
                text.setText(R.string.scene_1_2);
                break;
            case 3:
                imgScene.setImageResource(R.drawable.scene_1_3);
                text.setText(R.string.scene_1_3);
                break;
            case 4:
                imgScene.setImageResource(R.drawable.scene_1_4);
                text.setText(R.string.scene_1_4);
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
        dbHelper.saveLastSubscene("Scene1", sceneIndex,null,null);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Scene1")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Scene1", sceneIndex, null, null); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
