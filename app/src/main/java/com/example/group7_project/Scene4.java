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

public class Scene4 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
    String book;
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

        // รับค่าจาก Intent
        int subscene = getIntent().getIntExtra("subscene", 1);
        color = getIntent().getStringExtra("color");
        book = getIntent().getStringExtra("book");
        String result = getIntent().getStringExtra("result");

        // ตั้งค่า sceneIndex ให้ตรงกับ subscene
        sceneIndex = subscene;

        if ("overtime".equals(result)) {
            handleOvertime(color, book);
        } else if ("yes".equals(book)||"no".equals(book)){
            handleBook(color,book);
        } else {
            setupInitialScene(color);
        }

    }

    private void handleBook(String color,String book) {

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene4",sceneIndex,book,color);
        });

        if ("yes".equals(book)) {
            imgScene.setImageResource(R.drawable.scene_4_3_2);
            text.setText(R.string.scene_4_3_2);

            btnNext.setOnClickListener(v -> {
                afterBook(color, text, imgScene, btnNext, sceneIndex,book);
            });
        }else{
            afterBook(color, text, imgScene, btnNext, sceneIndex,book);
        }
    }
    private void afterBook(String color, TextView text, ImageView imgScene, Button btnNext, int sceneNumber,String book) {
        sceneIndex = sceneNumber;

        loadSubsceneAfterBook(sceneIndex, imgScene, text);

        btnNext.setOnClickListener(v -> {
            sceneIndex++;
            if (sceneIndex > 3) {
                Intent intent = new Intent(Scene4.this, Action6.class);
                intent.putExtra("color", color);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            } else {
                loadSubsceneAfterBook(sceneIndex, imgScene, text);
            }
        });
    }
    private void loadSubsceneAfterBook(int sceneIndex, ImageView imgScene, TextView text) {
        switch (sceneIndex) {
            case 1:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_white);
                }
                text.setText(R.string.scene_4_4);
                break;
            case 2:
                imgScene.setImageResource(R.drawable.scene_4_5_1);
                text.setText(R.string.scene_4_5_1);
                break;
            case 3:
                imgScene.setImageResource(R.drawable.scene_4_5_1);
                text.setText(R.string.scene_4_5_2);
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null);
                break;
        }
    }

    private void setupInitialScene(String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_1_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_1_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_1_white);
        }
        text.setText(R.string.scene_4_1);

        final int[] sceneIndex = {1}; // ตัวนับสถานะ Scene

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    imgScene.setImageResource(R.drawable.scene_4_2);
                    text.setText(R.string.scene_4_2);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    imgScene.setImageResource(R.drawable.scene_4_3);
                    text.setText(R.string.scene_4_3_1);
                    sceneIndex[0] = 3;
                    break;
                default:
                    Intent intent = new Intent(Scene4.this, Action5.class);
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
            }
        });
    }

    private void handleOvertime(String color, String book) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        imgScene.setImageResource(R.drawable.scene_4_6_1);
        text.setText(R.string.scene_4_6_1);

        final int[] sceneIndex = {1};
        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    if ("black".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_4_6_2_black);
                    } else if ("orange".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_4_6_2_orange);
                    } else if ("white".equals(color)) {
                        imgScene.setImageResource(R.drawable.scene_4_6_2_white);
                    }
                    text.setText(R.string.scene_4_6_2);
                    sceneIndex[0] = 2;
                    break;
                default:
                    Intent intent = new Intent(Scene4.this, Ending.class);
                    intent.putExtra("color", color);
                    intent.putExtra("book", book);
                    intent.putExtra("endingType", "bad");
                    startActivity(intent);
                    break;
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Scene4", sceneIndex,book,color);
        dbHelper.close();
    }
}
