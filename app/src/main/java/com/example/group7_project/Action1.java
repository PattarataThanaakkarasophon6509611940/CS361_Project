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
        btnBlack.setOnClickListener(v -> sendColor("black"));
        btnOrange.setOnClickListener(v -> sendColor("orange"));
        btnWhite.setOnClickListener(v -> sendColor("white"));

    }
    // ฟังก์ชันสำหรับส่งผลลัพธ์กลับไปยัง Scene2
    private void sendColor(String color) {
        Intent intent = new Intent(Action1.this, Scene2.class);
        intent.putExtra("color", color);
        startActivity(intent);

    }
}
