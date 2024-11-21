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

public class Scene1 extends AppCompatActivity {

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

        // ตัวนับสถานะของ Scene 1
        final int[] sceneIndex = {1};

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    imgScene.setImageResource(R.drawable.scene_1_2);
                    text.setText(R.string.scene_1_2);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    imgScene.setImageResource(R.drawable.scene_1_3);
                    text.setText(R.string.scene_1_3);
                    sceneIndex[0] = 3;
                    break;
                case 3:
                    imgScene.setImageResource(R.drawable.scene_1_4);
                    text.setText(R.string.scene_1_4);
                    sceneIndex[0] = 4;
                    break;
                default:
                    // เมื่อจบ Scene 1 ให้ไปที่ Action1
                    Intent intent = new Intent(Scene1.this, Action1.class);
                    startActivity(intent);
                    break;
            }
        });

    }//end onCreate
}
