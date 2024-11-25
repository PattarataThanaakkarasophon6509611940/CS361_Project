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

public class EndingHappy extends AppCompatActivity {
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
        loadSubscene(sceneIndex, imgScene, text, color,ending);

        btnSetting.setOnClickListener(v -> {
            setting.showDialog("EndingHappy",sceneIndex,null,color);
        });
            btnNext.setOnClickListener(v -> {
                sceneIndex++;
                if (sceneIndex>7) {
                    Intent intent = new Intent(EndingHappy.this, TakePhoto.class);
                    intent.putExtra("color", color);
                    startActivity(intent);
                    finish();
                } else {
                    loadSubscene(sceneIndex, imgScene, text, color,ending);
                }
            });
    }

    private void loadSubscene(int sceneIndex,ImageView imgScene,TextView text, String color,TextView ending) {
        switch (sceneIndex) {
        case 1:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_white);
            }
            text.setText(R.string.scene_h_1_1);
            break;
        case 2:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_white);
            }
            text.setText(R.string.scene_h_1_2);
            break;
        case 3:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_1_1_white);
            }
            text.setText(R.string.scene_h_1_3);
            break;
        case 4:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_white);
            }
            text.setText(R.string.scene_h_2_1);
            break;
        case 5:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_white);
            }
            text.setText(R.string.scene_h_2_2);
            break;
        case 6:
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_2_1_white);
            }
            text.setText(R.string.scene_h_2_3);
            break;
        case 7:
            Button btnNext = findViewById(R.id.btnNext);
            Button btnTakePhoto= findViewById(R.id.btnTakePhoto);
            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_3_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_3_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_h_3_white);
            }
            text.setText(R.string.scene_h_3);
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
        dbHelper.saveLastSubscene("EndingHappy", sceneIndex,null,color);
        dbHelper.close();
    }
}
