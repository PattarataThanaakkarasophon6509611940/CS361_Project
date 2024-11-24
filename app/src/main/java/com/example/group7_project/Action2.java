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

public class Action2 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
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
            Intent intent = new Intent(Action2.this, Ending.class);
            intent.putExtra("color", color);
            intent.putExtra("endingType", "bad");
            startActivity(intent);
            dbHelper.close();
            finish();
        });

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action2.this, Scene3.class);
            intent.putExtra("color", color);
            startActivity(intent);
            dbHelper.close();
            finish();
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Action2", sceneIndex,null,color);
        dbHelper.close();
    }
}
