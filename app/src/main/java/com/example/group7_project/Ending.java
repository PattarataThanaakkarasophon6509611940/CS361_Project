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
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_sc_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_sc_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_sc_1_white);
                }
                text.setText(R.string.scene_sc_1);

                final int[] sceneIndexSecret = {1};

                btnNext.setOnClickListener(v -> {
                    switch (sceneIndexSecret[0]) {
                        case 1:
                            imgScene.setImageResource(R.drawable.scene_sc_2_1);
                            text.setText(R.string.scene_sc_2_1);
                            sceneIndexSecret[0] = 2;
                            break;
                        case 2:
                            text.setText(R.string.scene_sc_2_2);
                            sceneIndexSecret[0] = 3;
                            break;
                        case 3:
                            text.setText(R.string.scene_sc_2_3);
                            sceneIndexSecret[0] = 4;
                            break;
                        case 4:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                            }
                            text.setText(R.string.scene_sc_3);
                            sceneIndexSecret[0] = 5;
                            break;
                        case 5:
                            imgScene.setImageResource(R.drawable.scene_sc_4);
                            text.setText(R.string.scene_sc_4);
                            sceneIndexSecret[0] = 6;
                            break;
                        case 6:
                            imgScene.setImageResource(R.drawable.scene_sc_5_1);
                            text.setText(R.string.scene_sc_5_1);
                            sceneIndexSecret[0] = 7;
                            break;
                        case 7:
                            text.setText(R.string.scene_sc_5_2);
                            sceneIndexSecret[0] = 8;
                            break;
                        case 8:
                            text.setText(R.string.scene_sc_5_3);
                            sceneIndexSecret[0] = 9;
                            break;
                        case 9:
                            setContentView(R.layout.scene_ending_photo);
                            imgScene.setImageResource(R.drawable.scene_sc_6);
                            text.setText(R.string.scene_sc_6);
                            endingText.setText(R.string.ending);
                            sceneIndexSecret[0] = 10;
                            break;
                        default:
                            Intent intent = new Intent(Ending.this, TakePhoto.class);
                            intent.putExtra("color", color);
                            startActivity(intent);
                            break;
                    }
                });

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
                            setContentView(R.layout.scene_ending_photo);
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
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                }
                text.setText(R.string.scene_s_1_1);

                final int[] sceneIndexSad = {1};

                btnNext.setOnClickListener(v -> {
                    switch (sceneIndexSad[0]) {
                        case 1:
                            text.setText(R.string.scene_s_1_2);
                            sceneIndexSad[0] = 2;
                            break;
                        case 2:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_black);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_orange);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_white);
                            }
                            text.setText(R.string.scene_s_2);
                            sceneIndexSad[0] = 3;
                            break;
                        case 3:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_3_black);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_3_orange);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_3_white);
                            }
                            text.setText(R.string.scene_s_3);
                            sceneIndexSad[0] = 4;
                            break;
                        case 4:
                            imgScene.setImageResource(R.drawable.scene_s_4);
                            text.setText(R.string.scene_s_4);
                            sceneIndexSad[0] = 5;
                            break;
                        case 5:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_5_1_black);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_5_1_orange);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_s_5_1_white);
                            }
                            text.setText(R.string.scene_s_5_1);
                            sceneIndexSad[0] = 6;
                            break;
                        case 6:
                            text.setText(R.string.scene_sc_5_2);
                            sceneIndexSad[0] = 7;
                            break;
                        case 7:
                            text.setText(R.string.scene_sc_5_3);
                            sceneIndexSad[0] = 8;
                            break;
                        case 8:
                            setContentView(R.layout.scene_ending_photo);
                            imgScene.setImageResource(R.drawable.scene_s_6);
                            text.setText(R.string.scene_s_6);
                            endingText.setText(R.string.ending);
                            sceneIndexSad[0] = 9;
                            break;
                        default:
                            Intent intent = new Intent(Ending.this, TakePhoto.class);
                            intent.putExtra("color", color);
                            startActivity(intent);
                            break;
                    }
                });
                break;
            case "true":
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_5_4_1_white);
                }
                text.setText(R.string.scene_t_1_1);

                final int[] sceneIndexTrue = {1};

                btnNext.setOnClickListener(v -> {
                    switch (sceneIndexTrue[0]) {
                        case 1:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_black);
                                text.setText(R.string.scene_t_1_2);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_orange);
                                text.setText(R.string.scene_t_1_2);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_5_2_white);
                                text.setText(R.string.scene_t_1_2);
                            }
                            sceneIndexTrue[0] = 2;
                            break;
                        case 2:
                            if ("black".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_t_2_1_black);
                                text.setText(R.string.scene_t_2_1);
                            } else if ("orange".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_t_2_1_orange);
                                text.setText(R.string.scene_t_2_1);
                            } else if ("white".equals(color)) {
                                imgScene.setImageResource(R.drawable.scene_t_2_1_white);
                                text.setText(R.string.scene_t_2_1);
                            }
                            sceneIndexTrue[0] = 3;
                            break;
                        case 3:
                            setContentView(R.layout.scene_ending_photo);
                            if ("black".equals(color)) {
                                text.setText(R.string.scene_t_2_2);
                            } else if ("orange".equals(color)) {
                                text.setText(R.string.scene_t_2_2);
                            } else if ("white".equals(color)) {
                                text.setText(R.string.scene_t_2_2);
                                endingText.setText(R.string.ending);
                            }
                            sceneIndexTrue[0] = 4;
                            break;
                        default:
                            Intent intent = new Intent(Ending.this, TakePhoto.class);
                            intent.putExtra("color", color);
                            startActivity(intent);
                            break;
                    }
                });
                break;
            case "happy":
                if ("black".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_black);
                } else if ("orange".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_orange);
                } else if ("white".equals(color)) {
                    imgScene.setImageResource(R.drawable.scene_h_1_1_white);
                }
                text.setText(R.string.scene_h_1_1);

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
                            setContentView(R.layout.scene_ending_photo);
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
