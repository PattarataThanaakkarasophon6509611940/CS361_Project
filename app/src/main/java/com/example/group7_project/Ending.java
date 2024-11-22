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

public class Ending extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_ending);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String color = getIntent().getStringExtra("color");
        String endingType = getIntent().getStringExtra("endingType");

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);
        TextView endingText = findViewById(R.id.ending);

        // กำหนดฉากจบตาม endingType
        switch (endingType) {
            case "secret":


                break;
            case "bad":
                imgScene.setImageResource(R.drawable.scene_b_1);
                text.setText(R.string.scene_b_1);
                final int[] sceneIndexBad = {1};

                btnNext.setOnClickListener(v -> {
                    switch (sceneIndexBad[0]) {
                        case 1:
                            imgScene.setImageResource(R.drawable.scene_b_2);
                            text.setText(R.string.scene_b_2);
                            sceneIndexBad[0] = 2;
                            break;
                        case 2:
                            imgScene.setImageResource(R.drawable.scene_b_3);
                            text.setText(R.string.scene_b_3);
                            endingText.setText(R.string.ending);
                            sceneIndexBad[0] = 3;
                            break;
                        default:
                            Intent intent = new Intent(Ending.this, TakePhoto.class);
                            intent.putExtra("color", color);
                            startActivity(intent);
                            break;
                    }
                });
                break;
            case "sad":

                break;
            case "true":

                break;
            case "happy":
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_black);
                    text.setText(R.string.scene_h_1_1);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_orange);
                    text.setText(R.string.scene_h_1_1);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_white);
                    text.setText(R.string.scene_h_1_1);
                }

                final int[] sceneIndexHappy = {1};

                btnNext.setOnClickListener(v -> {
                    switch (sceneIndexHappy[0]) {
                        case 1:
                            if ("black".equals(color)) {
                                text.setText(R.string.scene_h_1_2);
                            } else if ("orange".equals(color)) {
                                text.setText(R.string.scene_h_1_2);
                            } else if ("white".equals(color)) {
                                text.setText(R.string.scene_h_1_2);
                            }
                            sceneIndexHappy[0] = 2;
                            break;
                        case 2:
                            if ("black".equals(color)) {
                                text.setText(R.string.scene_h_1_3);
                            } else if ("orange".equals(color)) {
                                text.setText(R.string.scene_h_1_3);
                            } else if ("white".equals(color)) {
                                text.setText(R.string.scene_h_1_3);
                            }
                            sceneIndexHappy[0] = 3;
                            break;
                        case 3:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_2_1_black);
                                text.setText(R.string.scene_h_2_1);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_2_1_orange);
                                text.setText(R.string.scene_h_2_1);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_2_1_white);
                                text.setText(R.string.scene_h_2_1);
                            }
                            sceneIndexHappy[0] = 4;
                            break;
                        case 4:
                            if ("black".equals(color)) {
                                text.setText(R.string.scene_h_2_2);
                            } else if ("orange".equals(color)) {
                                text.setText(R.string.scene_h_2_2);
                            } else if ("white".equals(color)) {
                                text.setText(R.string.scene_h_2_2);
                            }
                            sceneIndexHappy[0] = 5;
                            break;
                        case 5:
                            if ("black".equals(color)) {
                                text.setText(R.string.scene_h_2_3);
                            } else if ("orange".equals(color)) {
                                text.setText(R.string.scene_h_2_3);
                            } else if ("white".equals(color)) {
                                text.setText(R.string.scene_h_2_3);
                            }
                            sceneIndexHappy[0] = 6;
                            break;
                        case 6:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_3_black);
                                text.setText(R.string.scene_h_3);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_3_orange);
                                text.setText(R.string.scene_h_3);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_h_3_white);
                                text.setText(R.string.scene_h_3);
                                endingText.setText(R.string.ending);
                            }
                            sceneIndexHappy[0] = 7;
                            break;
                        default:
                            Intent intent = new Intent(Ending.this, TakePhoto.class);
                            intent.putExtra("color", color);
                            startActivity(intent);
                            break;
                    }
                });

                break;
            default:
        }
    }
}
