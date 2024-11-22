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

        String color = getIntent().getStringExtra("color");
        String book = getIntent().getStringExtra("book");
        String result = getIntent().getStringExtra("result");

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

        final int[] sceneIndex = {1};

        if ("yes".equals(book)) {
            imgScene.setImageResource(R.drawable.scene_4_3_2);
            text.setText(R.string.scene_4_3_2);
        }

        if ("black".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_4_black);
        } else if ("orange".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_4_orange);
        } else if ("white".equals(color)) {
            imgScene.setImageResource(R.drawable.scene_4_4_white);
        }
        text.setText(R.string.scene_4_4);

        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    imgScene.setImageResource(R.drawable.scene_4_5_1);
                    text.setText(R.string.scene_4_5_1);
                    sceneIndex[0] = 2;
                    break;
                case 2:
                    text.setText(R.string.scene_4_5_2);
                    sceneIndex[0] = 3;
                    break;
                default:
                    Intent intent = new Intent(Scene4.this, Action6.class);
                    intent.putExtra("color", color);
                    intent.putExtra("book", book);
                    startActivity(intent);
                    break;
            }
        });


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
}
