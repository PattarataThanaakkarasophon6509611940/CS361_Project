package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Scene5 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
    String book;
    private long backPressedTime =0;
    @Override
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

        int subscene = getIntent().getIntExtra("subscene", 1);
        color = getIntent().getStringExtra("color");
        book = getIntent().getStringExtra("book");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        sceneIndex = subscene;
        final int[] sceneNumber = {subscene};

        btnSetting.setOnClickListener(v -> {
            sceneIndex = sceneNumber[0];
            setting.showDialog("Scene5",sceneIndex,book,color);
        });

        if("yes".equals(book)){
            Intent intent = new Intent(Scene5.this, EndingSecret.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        }else{
            System.out.println(sceneNumber[0]);
            loadSubscene(sceneIndex, imgScene, text,color);
        }

        btnNext.setOnClickListener(v -> {
            sceneNumber[0]++;
            sceneIndex = sceneNumber[0];
            if (sceneNumber[0] > 10) {
                Intent intent = new Intent(Scene5.this, Action7.class);
                intent.putExtra("color", color);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            } else {
                loadSubscene(sceneNumber[0], imgScene, text,color);
            }
        });
    }
    private void loadSubscene(int sceneNumber, ImageView imgScene, TextView text, String color) {
        switch (sceneNumber) {
            case 1:
                System.out.println("case 1 : "+sceneNumber);
                imgScene.setImageResource(R.drawable.scene_5_1);
                text.setText(R.string.scene_5_1);
                break;
            case 2:
                System.out.println("case 2 : "+sceneNumber);
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_2_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_2_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_2_white);
                }
                text.setText(R.string.scene_5_2);
                break;
            case 3:
                imgScene.setImageResource(R.drawable.scene_5_3_1);
                text.setText(R.string.scene_5_3_1);
                break;
            case 4:
                imgScene.setImageResource(R.drawable.scene_5_3_1);
                text.setText(R.string.scene_5_3_2);
                break;
            case 5:
                imgScene.setImageResource(R.drawable.scene_5_3_1);
                text.setText(R.string.scene_5_3_3);
                break;
            case 6:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                }
                text.setText(R.string.scene_5_4_1);
                break;
            case 7:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                }
                text.setText(R.string.scene_5_4_2);
                break;
            case 8:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                }
                text.setText(R.string.scene_5_4_3);
                break;
            case 9:
                imgScene.setImageResource(R.drawable.scene_5_3_1);
                text.setText(R.string.scene_5_5_1);
                break;
            case 10:
                imgScene.setImageResource(R.drawable.scene_5_3_1);
                text.setText(R.string.scene_5_5_2);
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
            saveSceneState(); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
    private void saveSceneState() {
        dbHelper.saveLastSubscene("Scene5", sceneIndex, book, color);
        dbHelper.close();
    }
}
