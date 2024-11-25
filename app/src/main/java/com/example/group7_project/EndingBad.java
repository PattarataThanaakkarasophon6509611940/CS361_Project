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

public class EndingBad extends AppCompatActivity {
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
        setContentView(R.layout.scene_ending);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        color = getIntent().getStringExtra("color");
        int subscene = getIntent().getIntExtra("subscene",1);

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        TextView ending = findViewById(R.id.ending);
        ImageView btnSetting = findViewById(R.id.imgSetting);

        sceneIndex = subscene;
        loadSubscene(sceneIndex, imgScene, text, ending);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("EndingBad",sceneIndex,null,color);
        });
        btnNext.setOnClickListener(v -> {
            sceneIndex++;
            if (sceneIndex>3) {
                Intent intent = new Intent(EndingBad.this, TakePhoto.class);
                intent.putExtra("color", color);
                startActivity(intent);
                finish();
            } else {
                loadSubscene(sceneIndex, imgScene, text,ending);
            }
        });
    }
    private void loadSubscene(int sceneIndex,ImageView imgScene,TextView text, TextView ending) {
        switch (sceneIndex) {
            case 1:
                imgScene.setImageResource(R.drawable.scene_b_1);
                text.setText(R.string.scene_b_1);
                break;
            case 2:
                imgScene.setImageResource(R.drawable.scene_b_2);
                text.setText(R.string.scene_b_2);
                break;
            case 3:
                Button btnNext = findViewById(R.id.btnNext);
                Button btnTakePhoto= findViewById(R.id.btnTakePhoto);

                imgScene.setImageResource(R.drawable.scene_b_3);
                text.setText(R.string.scene_b_3);
                btnNext.setVisibility(View.GONE);
                btnTakePhoto.setVisibility(View.VISIBLE);
                ending.setText(R.string.ending);
                break;
            default:
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("EndingBad", sceneIndex,null,color);
        dbHelper.close();
    }
}