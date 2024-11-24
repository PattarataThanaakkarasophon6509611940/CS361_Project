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
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
    String status;
    @Override
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

        color = getIntent().getStringExtra("color");
        int subscene = getIntent().getIntExtra("subscene",1);
        status = getIntent().getStringExtra("status");

        // ตั้งค่า sceneIndex ให้ตรงกับ subscene
        sceneIndex = subscene;

        if("fail".equals(status)){
            startFail(8,color);
        } else if (sceneIndex>8) {
            startFail(subscene,color);
        } else if ("pass".equals(status)){
            startPass(4,color);
        }else if (sceneIndex>3){
            startPass(subscene,color);
        } else{
            startNormal(sceneIndex,color);
        }
    }

    private void startNormal(int subscene,String color){
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {subscene};

        loadSubscene(subNumber[0], imgScene, text);

        btnSetting.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            setting.showDialog("Scene3", subNumber[0],null,color);
        });

        btnNext.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            subNumber[0]++;
            if (subNumber[0] > 3) {
                Intent intent = new Intent(Scene3.this, Action3.class);
                intent.putExtra("color", color);
                startActivity(intent);
            } else {
                loadSubscene(subNumber[0], imgScene, text);
            }
        });
    }
    private void loadSubscene(int subNumber, ImageView imgScene, TextView text) {
        switch (subNumber) {
            case 1:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_1_white);
                }
                text.setText(R.string.scene_3_1);
                break;
            case 2:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_2_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_2_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_2_white);
                }
                text.setText(R.string.scene_3_2);
                break;
            case 3:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_3_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_3_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_3_white);
                }
                text.setText(R.string.scene_3_3);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null); // กรณีไม่มี subscene
                break;
        }
    }
    private void startPass(int subscene,String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {subscene};

        btnSetting.setOnClickListener(v -> {
            System.out.println("Pass subscene : "+sceneIndex);
            setting.showDialog("Scene3", subNumber[0],null,color);

        });

        loadPassSubscene(subNumber[0], imgScene, text,color);

        btnNext.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            subNumber[0]++;
            if (subNumber[0] > 7) {
                Intent intent = new Intent(Scene3.this, Action4.class);
                intent.putExtra("color", color);
                startActivity(intent);
            } else {
                loadPassSubscene(subNumber[0], imgScene, text,color);
            }
        });
    }
    private void loadPassSubscene(int subscene, ImageView imgScene, TextView text, String color) {
        switch (subscene) {
            case 4:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_white);
                }
                text.setText(R.string.scene_3_5_1);
                break;
            case 5:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_white);
                }
                text.setText(R.string.scene_3_5_2);
                break;
            case 6:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_white);
                }
                text.setText(R.string.scene_3_5_3);
                break;
            case 7:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_5_white);
                }
                text.setText(R.string.scene_3_5_4);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null); // แสดงภาพว่างกรณีไม่มี subscene
                break;
        }
    }
    private void startFail(int subscene,String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {subscene};

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene3",subNumber[0],null,color);
        });

        loadFailSubscene(subNumber[0], imgScene, text,color);

        // การทำงานปุ่มถัดไป
        btnNext.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            subNumber[0]++;
            if (subNumber[0] > 9) {
                Intent intent = new Intent(Scene3.this, Ending.class);
                intent.putExtra("endingType", "bad");
                intent.putExtra("color", color);
                startActivity(intent);
            } else {
                loadFailSubscene(subNumber[0], imgScene, text,color);
            }
        });

    }
    private void loadFailSubscene(int sceneNumber, ImageView imgScene, TextView text, String color) {
        switch (sceneNumber) {
            case 8:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_white);
                }
                text.setText(R.string.scene_3_4_1);
                break;
            case 9:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_3_4_1_1_white);
                }
                text.setText(R.string.scene_3_4_2);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null);
                break;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Scene3", sceneIndex, null, color);
        dbHelper.close();
    }
}