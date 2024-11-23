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

public class Scene2 extends AppCompatActivity {

    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String color = getIntent().getStringExtra("color");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        setting = new Setting(this);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene1",sceneIndex);
        });

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_black);
            text.setText(R.string.scene_2_2_1);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_orange);
            text.setText(R.string.scene_2_2_1);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_2_2_white);
            text.setText(R.string.scene_2_2_1);
        }

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex) {
                case 1:
                    text.setText(R.string.scene_2_2_2);
                    sceneIndex = 2;
                    break;
                default:
                    Intent intent = new Intent(Scene2.this, Action2.class);
                    intent.putExtra("color", color);
                    startActivity(intent);
                    finish();
                    break;
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Scene2", sceneIndex);
    }
}