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

public class Scene4 extends AppCompatActivity {
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

        // รับค่าจาก Intent
        int subscene = getIntent().getIntExtra("subscene", 1);
        color = getIntent().getStringExtra("color");
        book = getIntent().getStringExtra("book");
        String result = getIntent().getStringExtra("result");

        sceneIndex = subscene;

        if ("overtime".equals(result)) {
            handleOvertime(color, book,8);
        } else if(sceneIndex>=8&&sceneIndex<=9){
            handleOvertime(color, book,sceneIndex);
        }else if(sceneIndex>=4&&sceneIndex<=7){
            handleBook(color, book, sceneIndex);
        }else if ("yes".equals(book)||"no".equals(book)) {
            handleBook(color, book,4);
        } else if(sceneIndex<4){
            setupInitialScene(sceneIndex ,color);
        }
    }

    private void handleBook(String color,String book,int sceneNumber) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        sceneIndex = sceneNumber;

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene4",sceneIndex,book,color);
        });

        if ("yes".equals(book)&&sceneNumber==4) {
            imgScene.setImageResource(R.drawable.scene_4_3_2);
            text.setText(R.string.scene_4_3_2);
            btnNext.setOnClickListener(v -> {
                afterBook(color, text, imgScene, btnNext, sceneNumber,book);
            });
        }else{
            afterBook(color, text, imgScene, btnNext, sceneNumber,book);
        }
    }
    private void afterBook(String color, TextView text, ImageView imgScene, Button btnNext, int sceneNumber,String book) {
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {sceneNumber};

        loadSubsceneAfterBook(sceneIndex, imgScene, text);

        btnSetting.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            setting.showDialog("Scene4",sceneIndex,book,color);
        });

        btnNext.setOnClickListener(v -> {
            subNumber[0]++;
            sceneIndex = subNumber[0];
            System.out.println("after book : "+subNumber[0]);
            if (subNumber[0] > 6) {
                Intent intent = new Intent(Scene4.this, Action6.class);
                intent.putExtra("color", color);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            } else {
                loadSubsceneAfterBook(subNumber[0], imgScene, text);
            }
        });
    }
    private void loadSubsceneAfterBook(int subNumber, ImageView imgScene, TextView text) {
        switch (subNumber) {
            case 4:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_4_white);
                }
                text.setText(R.string.scene_4_4);
                break;
            case 5:
                imgScene.setImageResource(R.drawable.scene_4_5_1);
                text.setText(R.string.scene_4_5_1);
                break;
            case 6:
                imgScene.setImageResource(R.drawable.scene_4_5_1);
                text.setText(R.string.scene_4_5_2);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null);
                break;
        }
    }

    private void setupInitialScene(int subscene,String color) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {subscene};

        btnSetting.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            setting.showDialog("Scene4",sceneIndex,book,color);
        });
        loadSubscene(sceneIndex, imgScene, text,color);

        btnNext.setOnClickListener(v -> {
            subNumber[0]++;
            sceneIndex = subNumber[0];
            if (subNumber[0] > 3) {
                Intent intent = new Intent(Scene4.this, Action5.class);
                intent.putExtra("color", color);
                startActivity(intent);
                finish();
            } else {
                loadSubscene(subNumber[0], imgScene, text,color);
            }
        });
    }
    private void loadSubscene(int sceneNumber, ImageView imgScene, TextView text, String color) {
        switch (sceneNumber) {
            case 1:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_1_white);
                }
                text.setText(R.string.scene_4_1);
                break;
            case 2:
                imgScene.setImageResource(R.drawable.scene_4_2);
                text.setText(R.string.scene_4_2);
                break;
            case 3:
                imgScene.setImageResource(R.drawable.scene_4_3);
                text.setText(R.string.scene_4_3_1);
                break;
            default:
                text.setText(R.string.invalidSubscene);
                imgScene.setImageDrawable(null); // แสดงภาพว่างกรณีไม่มี subscene
                break;
        }
    }
    private void handleOvertime(String color, String book,int sceneNumber) {
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        final int[] subNumber = {sceneNumber};

        btnSetting.setOnClickListener(v -> {
            sceneIndex = subNumber[0];
            setting.showDialog("Scene4",sceneIndex,book,color);
        });

        loadSubsceneOvertime(subNumber[0], imgScene, text,color);

        btnNext.setOnClickListener(v -> {
            subNumber[0]++;
            sceneIndex = subNumber[0];
            if (subNumber[0] > 9) {
                Intent intent = new Intent(Scene4.this, EndingBad.class);
                intent.putExtra("color", color);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            }else {
                loadSubsceneOvertime(subNumber[0], imgScene, text,color);
            }
        });
    }
    private void loadSubsceneOvertime(int sceneIndex, ImageView imgScene, TextView text, String color) {
        switch (sceneIndex) {
            case 8:
                imgScene.setImageResource(R.drawable.scene_4_6_1);
                text.setText(R.string.scene_4_6_1);
                break;
            case 9:
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_6_2_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_6_2_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_4_6_2_white);
                }
                text.setText(R.string.scene_4_6_2);
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
        dbHelper.saveLastSubscene("Scene4", sceneIndex,book,color);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Scene4")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Scene4", sceneIndex, null, null); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
