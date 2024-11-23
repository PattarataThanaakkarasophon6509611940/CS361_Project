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
        setContentView(R.layout.scene_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);

        final int[] sceneIndex = {1};

        String color = getIntent().getStringExtra("color");

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_1_white);
        }
        text.setText(R.string.scene_3_1);

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
                    // Intent ไปยัง Action3
                    Intent intent = new Intent(Scene3.this, Action3.class);
                    intent.putExtra("color", color); // ส่งค่าไปยัง Action3
                    startActivityForResult(intent, 1);
                    break;
            }
        });

    }
    // รับผลลัพธ์จาก Action3
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // รับค่า "status" (Pass หรือ Fail) และ "color" จาก Action3
            String status = data.getStringExtra("status");
            String color = data.getStringExtra("color");

            // แสดง Layout ตามผลลัพธ์
            if ("pass".equals(status)) {
                startPass(color);
            } else if ("fail".equals(status)) {
                startFail(color);
            }
        }
    }

    private void startPass(String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_5_white);
        }

        text.setText(R.string.scene_3_5_1);

        final int[] sceneIndex = {1}; // ตัวนับสถานะ Scene

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    text.setText(R.string.scene_3_5_2);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    text.setText(R.string.scene_3_5_3);
                    sceneIndex[0] = 3;
                    break;
                case 3:
                    text.setText(R.string.scene_3_5_4);
                    sceneIndex[0] = 4;
                    break;
                default:
                    Intent intent = new Intent(Scene3.this, Action4.class);
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
            }
        });
    }
    private void startFail(String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_4_1_1_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_4_1_1_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_3_4_1_1_white);
        }

        text.setText(R.string.scene_3_4_1);

        final int[] sceneIndex = {1}; // ตัวนับสถานะ Scene

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    text.setText(R.string.scene_3_4_2);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    text.setText(R.string.scene_3_4_3);
                    sceneIndex[0] = 3;
                    break;
                default:
                    btnNext.setOnClickListener(v2 -> {
                        Intent intent = new Intent(Scene3.this, Ending.class);
                        intent.putExtra("endingType", "bad");
                        intent.putExtra("color", color);
                        startActivity(intent);
                    });
                    break;
            }
        });
    }

}
