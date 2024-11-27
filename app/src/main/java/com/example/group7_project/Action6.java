package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private long remainingTime; // Store remaining time
    private boolean isPaused = false;
    private int sceneIndex = 1;
    private DatabaseHelper dbHelper;
    String color;
    String book;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.action_6);
        dbHelper = new DatabaseHelper(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        color = getIntent().getStringExtra("color");
        book = getIntent().getStringExtra("book");
        // Set status bar color to black
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        // Set navigation bar color to black
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, android.R.color.black));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView btnInformation = findViewById(R.id.btnInformation);
        timerText = findViewById(R.id.timerText); // TextView for displaying timer

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

        long totalTime = 30000;
        remainingTime = totalTime / 1000; // Initialize remainingTime
        startTimer(remainingTime);
        countDownTimer.start();

        // Show modal information popup on start
        showInformationDialog();

        btnInformation.setOnClickListener(v -> {
            showInformationDialog(); // Show the information modal again
        });
    }

    private void startTimer(long timeInSeconds) {
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

    private void showInformationDialog() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Pause the timer
        }

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.modal_info_action6); // Use your modal layout
        dialog.setCancelable(false); // Prevent dismissing without clicking btnOK

        Button btnOK = dialog.findViewById(R.id.btnOK);
        if (btnOK == null) {
            Log.e("Action3", "btnOK not found in modal_information layout");
            dialog.dismiss();
            return;
        }

        btnOK.setOnClickListener(v -> {
            dialog.dismiss(); // Close the dialog
            if (countPair != 3) {
                resumeTimer();
            }
        });

        dialog.show();
    }

    private void resumeTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Ensure any existing timer is stopped
        }
        if (remainingTime > 0) {
            startTimer(remainingTime); // Recreate the timer
            countDownTimer.start(); // Start the new timer
        }
    }

    private void navigateToPassLayout() {
        Intent intent = new Intent(Action6.this, Scene5.class);
        intent.putExtra("color", color);
        intent.putExtra("book", book);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Pause the timer
        }
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Resume the game timer if it was paused
        if (isPaused) {
            resumeTimer();
            isPaused = false; // Reset the pause flag
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("Action6", sceneIndex, book, color); // บันทึกข้อมูลก่อนออก
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
}
