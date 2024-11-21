package com.example.group7_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class Action1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final Button btnBlack = findViewById(R.id.btnBlack);
        final Button btnOrange = findViewById(R.id.btnOrange);
        final Button btnWhite = findViewById(R.id.btnWhite);

        // ตั้งค่า OnClickListener สำหรับปุ่มแต่ละปุ่ม
        btnBlack.setOnClickListener(v -> navigateToGameActivity("black"));
        btnOrange.setOnClickListener(v -> navigateToGameActivity("orange"));
        btnWhite.setOnClickListener(v -> navigateToGameActivity("white"));

    }
    // ฟังก์ชันสำหรับส่งผลลัพธ์กลับไปยัง GameActivity
    private void navigateToGameActivity(String color) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedColor", color);
        setResult(RESULT_OK, resultIntent);
        finish(); // ปิด Activity
    }
}
