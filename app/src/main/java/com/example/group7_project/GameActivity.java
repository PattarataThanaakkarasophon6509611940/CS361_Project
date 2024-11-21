package com.example.group7_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.scene_1t4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnNext = findViewById(R.id.btnNext);
        ImageView imgScene = findViewById(R.id.imgScene_1t4);
        TextView text = findViewById(R.id.textScene_1t4);

        // ตัวนับสถานะเพื่อเก็บสถานะของ Scene
        final int[] sceneIndex = {1}; // เริ่มต้นที่ Scene 1

        // เพิ่ม OnClickListener สำหรับปุ่ม Next
        btnNext.setOnClickListener(v -> {
            switch (sceneIndex[0]) {
                case 1:
                    imgScene.setImageResource(R.drawable.scene_1_2);
                    text.setText(R.string.Scene_1_2);
                    sceneIndex[0] = 2; // เปลี่ยนเป็น Scene 2
                    break;
                case 2:
                    imgScene.setImageResource(R.drawable.scene_1_3);
                    text.setText(R.string.Scene_1_3);
                    sceneIndex[0] = 3; // เปลี่ยนเป็น Scene 3
                    break;
                case 3:
                    imgScene.setImageResource(R.drawable.scene_1_4);
                    text.setText(R.string.Scene_1_4);
                    sceneIndex[0] = 4; // เปลี่ยนเป็น Scene 4
                    break;
                default:
                    // หาก Scene เกิน 5 ทำให้ปุ่มไม่ทำอะไร หรือรีเซ็ตเป็น Scene 1
                    break;
            }
        });

    }//end onCreate

}
