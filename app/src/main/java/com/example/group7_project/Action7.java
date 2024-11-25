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

public class Action7  extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;
    String color;
    String book;
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

        // รับค่า color จาก Intent
        color = getIntent().getStringExtra("color");
        int subscene = getIntent().getIntExtra("subscene",1);

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnYes = findViewById(R.id.btnYes);
        Button btnNo = findViewById(R.id.btnNo);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        sceneIndex = subscene;

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Action7",sceneIndex,book,color);
        });

        imgScene.setImageResource(R.drawable.scene_5_3_1);
        text.setText(R.string.action_7);
        btnNo.setText(R.string.action_7_choice_1);
        btnYes.setText(R.string.action_7_choice_2);

        btnNo.setOnClickListener(v -> {
            Intent intent = new Intent(Action7.this, EndingSad.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });

        btnYes.setOnClickListener(v -> {
            Intent intent = new Intent(Action7.this, EndingTrue.class);
            intent.putExtra("color", color);
            startActivity(intent);
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Action7", sceneIndex,null,color);
        dbHelper.close();
    }
}
