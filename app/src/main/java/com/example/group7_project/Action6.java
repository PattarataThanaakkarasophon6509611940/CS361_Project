package com.example.group7_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Action6 extends AppCompatActivity {
    ImageView curView = null;
    private int countPair = 0;
    final int[] drawable = new int[]{
            R.drawable.action6_flower,
            R.drawable.action6_moon,
            R.drawable.action6_sun,
    };
    int[] pos = {0, 1, 2, 0, 1, 2};
    int currentPos = -1;
    TextView timerText;
    CountDownTimer countDownTimer;

    // Declare color and book at the class level
    String color;
    String book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set status bar color to black
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        // Set navigation bar color to black
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, android.R.color.black));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerText = findViewById(R.id.timerText); // TextView for displaying timer

        // Start the timer
        startTimer();

        ImageAdapter imageAdapter = new ImageAdapter(this);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentPos < 0) {
                    currentPos = position;
                    curView = (ImageView) view;
                    ((ImageView) view).setImageResource(drawable[pos[position]]);
                } else {
                    if (currentPos == position) {
                        ((ImageView) view).setImageResource(R.drawable.action6_question);
                    } else if (pos[currentPos] != pos[position]) {
                        curView.setImageResource(R.drawable.action6_question);
                        Toast toast = Toast.makeText(Action6.this, getString(R.string.action6_notMatch), Toast.LENGTH_LONG);
                        toast.setView(null); // Remove any default view or icon
                        toast.show();
                    } else {
                        ((ImageView) view).setImageResource(drawable[pos[position]]);
                        countPair++;
                        if (countPair == 3) {
                            countDownTimer.cancel(); // Stop the timer if the user wins
                            navigateToPassLayout(); // Navigate to pass layout
                        }
                    }
                    currentPos = -1;
                }
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(15000, 1000) { // 30 seconds, tick every 1 second
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.format("%s %d %s", getString(R.string.action6_textLeft), millisUntilFinished / 1000, getString(R.string.action6_textRight)));
            }

            @Override
            public void onFinish() {
                Toast toast = Toast.makeText(Action6.this, getString(R.string.action6_timeUp), Toast.LENGTH_LONG);
                toast.setView(null); // Remove any default view or icon
                toast.show();
                navigateToFailLayout(); // Navigate to fail layout
            }
        };
        countDownTimer.start();
    }

    private void navigateToPassLayout() {
        Intent intent = new Intent(Action6.this, Scene5.class);
        intent.putExtra("color", color);
        intent.putExtra("book", book);
        intent.putExtra("result", "overtime");
        startActivity(intent);
        finish(); // Optional: Close current activity
    }

    private void navigateToFailLayout() {
        Intent intent = new Intent(Action6.this, Scene4.class);
        intent.putExtra("color", color);
        intent.putExtra("book", book);
        intent.putExtra("result", "overtime");
        startActivity(intent);
        finish(); // Optional: Close current activity
    }
}
