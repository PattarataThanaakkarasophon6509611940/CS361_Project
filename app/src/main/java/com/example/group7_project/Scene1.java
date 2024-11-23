package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Scene1 extends AppCompatActivity {
    private int sceneIndex = 1;
    private Setting setting;
    private DatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.scene_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        loadSubscene(sceneIndex, imgScene, text);

        setting = new Setting(this);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("Scene1",sceneIndex);
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
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("Scene1", sceneIndex);
    }
}
