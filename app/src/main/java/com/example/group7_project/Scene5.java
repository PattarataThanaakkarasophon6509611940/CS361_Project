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

public class Scene5 extends AppCompatActivity {
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

        ImageView imgScene = findViewById(R.id.scene);
        TextView text = findViewById(R.id.textScene);
        Button btnNext = findViewById(R.id.btnNext);

        imgScene.setImageResource(R.drawable.scene_5_1);
        text.setText(R.string.scene_5_1);

        if("yes".equals(book)){
            Intent intent = new Intent(Scene5.this, Ending.class);
            intent.putExtra("color", color);
            intent.putExtra("endingType", "secret");
            startActivity(intent);
            finish();
        }else{

            if ("black".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_5_2_black);
            } else if ("orange".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_5_2_orange);
            } else if ("white".equals(color)) {
                imgScene.setImageResource(R.drawable.scene_5_2_white);
            }
        }

    }
}
