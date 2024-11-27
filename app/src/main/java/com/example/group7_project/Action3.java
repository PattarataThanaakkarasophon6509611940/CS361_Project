package com.example.group7_project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.widget.Button;


public class Action3 extends AppCompatActivity {
    private final int[][] resourceIds = {
            {R.drawable.action3_row_1_column_1, R.drawable.action3_row_1_column_2, R.drawable.action3_row_1_column_3},
            {R.drawable.action3_row_2_column_1, R.drawable.action3_row_2_column_2, R.drawable.action3_row_2_column_3},
            {R.drawable.action3_row_3_column_1, R.drawable.action3_row_3_column_2, R.drawable.action3_row_3_column_3}
    };

    private CountDownTimer timer;
    private Button btnNext;
    private TextView timerTextView;
    private long remainingTime; // Store remaining time
    private boolean isPaused = false;
    private int sceneIndex = 1;
    private String color;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_3);

        // Set status bar color to black
        getWindow().setStatusBarColor(Color.BLACK);

        // Set navigation bar color to black
        getWindow().setNavigationBarColor(Color.BLACK);

        // Optional: Ensure system UI elements (icons) are visible on a dark background
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE); // Initially hide the button
        color = getIntent().getStringExtra("color");

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Action3.this, Scene3.class);
                intent.putExtra("color", color);
                intent.putExtra("status", "pass");
                startActivity(intent);
                finish();
            }
        });
        ImageView btnInformation = findViewById(R.id.btnInformation);
        timerTextView = findViewById(R.id.timerText); // Assuming you have this ID in your layout

        // Logic to start the game and timer
        List<LinearLayout> linearLayouts = new ArrayList<>();
        List<Integer> imageResourceIds = new ArrayList<>();
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        gridLayout.post(() -> {
            int gridWidth = gridLayout.getWidth();
            int cellSize = gridWidth / 3;

            ViewGroup.LayoutParams layoutParams = gridLayout.getLayoutParams();
            layoutParams.height = cellSize * 3;
            gridLayout.setLayoutParams(layoutParams);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    LinearLayout linearLayout = new LinearLayout(Action3.this);
                    linearLayouts.add(linearLayout);

                    linearLayout.setOnDragListener(new MyDragListener());

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                            GridLayout.spec(i, 1f),
                            GridLayout.spec(j, 1f)
                    );
                    params.setMargins(3, 3, 3, 3);
                    params.width = 0;
                    params.height = 0;
                    linearLayout.setLayoutParams(params);
                    linearLayout.setGravity(Gravity.CENTER);

                    CustomImageView customImageView = new CustomImageView(Action3.this);
                    customImageView.setId(View.generateViewId());
                    imageResourceIds.add(customImageView.getId());

                    customImageView.setImageResource(resourceIds[i][j]);
                    customImageView.setAdjustViewBounds(true);
                    customImageView.setScaleType(CustomImageView.ScaleType.CENTER_CROP);
                    customImageView.setOnTouchListener(new MyTouchListener());

                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );
                    imageParams.gravity = Gravity.CENTER;
                    customImageView.setLayoutParams(imageParams);

                    linearLayout.addView(customImageView);
                    gridLayout.addView(linearLayout);
                }
            }

            Collections.shuffle(imageResourceIds);
            for (int i = 0; i < linearLayouts.size(); i++) {
                CustomImageView customImageView = findViewById(imageResourceIds.get(i));
                if (customImageView == null) continue;
                ViewParent parent = customImageView.getParent();
                if (parent instanceof ViewGroup) ((ViewGroup) parent).removeView(customImageView);
                linearLayouts.get(i).addView(customImageView);
            }
        });

        long totalTime = 30000;
        remainingTime = totalTime / 1000; // Initialize remainingTime
        createTimer(remainingTime);
        timer.start();

        // Show modal information popup on start
        showInformationDialog();

        btnInformation.setOnClickListener(v -> {
            showInformationDialog(); // Show the information modal again
        });

    }

    private void createTimer(long timeInSeconds) {
        timer = new CountDownTimer(timeInSeconds * 1000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished / 1000;
                timerTextView.setText(String.format("%s %d %s",
                        getString(R.string.action3_textLeft),
                        remainingTime,
                        getString(R.string.action3_textRight)));
            }

            @Override
            public void onFinish() {
                navigateToResult(isGameCompleted());
            }
        };
    }


    private void showInformationDialog() {
        if (timer != null) {
            timer.cancel(); // Pause the timer
        }

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.modal_information); // Use your modal layout
        dialog.setCancelable(false); // Prevent dismissing without clicking btnOK

        Button btnOK = dialog.findViewById(R.id.btnOK);
        if (btnOK == null) {
            Log.e("Action3", "btnOK not found in modal_information layout");
            dialog.dismiss();
            return;
        }

        btnOK.setOnClickListener(v -> {
            dialog.dismiss(); // Close the dialog
            if (!isGameCompleted()) {
                resumeTimer();
            }
        });

        dialog.show();
    }

    private void resumeTimer() {
        if (timer != null) {
            timer.cancel(); // Ensure any existing timer is stopped
        }
        if (remainingTime > 0) {
            createTimer(remainingTime); // Recreate the timer
            timer.start(); // Start the new timer
        }
    }

    private void navigateToResult(boolean success) {
        // Stop the timer to ensure it's not running
        if (timer != null) {
            timer.cancel(); // Cancel the timer
        }

        if (success) {
            btnNext.setVisibility(View.VISIBLE);
            // Set button click listener for navigation
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Action3.this, Scene3.class);
                    intent.putExtra("color", color);
                    intent.putExtra("status", "pass");
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Intent intent = new Intent(Action3.this, Scene3.class);
            intent.putExtra("color", color);
            intent.putExtra("status", "fail");
            startActivity(intent);
            finish();

        }
    }

    private boolean isGameCompleted() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View childView = gridLayout.getChildAt(i);

            if (!(childView instanceof LinearLayout)) continue;

            LinearLayout linearLayout = (LinearLayout) childView;

            if (linearLayout.getChildCount() != 1) return false;

            View innerChildView = linearLayout.getChildAt(0);

            if (!(innerChildView instanceof CustomImageView)) return false;

            CustomImageView customImageView = (CustomImageView) innerChildView;

            Drawable currentDrawable = customImageView.getDrawable();

            int row = i / 3;
            int column = i % 3;

            int expectedResourceId = resourceIds[row][column];
            Drawable expectedDrawable = ResourcesCompat.getDrawable(getResources(), expectedResourceId, null);

            if (!areDrawablesEqual(currentDrawable, expectedDrawable)) {
                return false;
            }
        }

        // If all images are in the correct place
        return true;
    }

    // Helper method to compare drawables
    private boolean areDrawablesEqual(Drawable drawable1, Drawable drawable2) {
        if (drawable1 == null && drawable2 == null) return true;
        if (drawable1 == null || drawable2 == null) return false;

        Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();

        return bitmap1.sameAs(bitmap2);
    }

    private static final class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);

                // Call performClick for accessibility compliance
                view.performClick();
                return true;
            }
            return false;
        }
    }

    class MyDragListener implements View.OnDragListener {
        private final Drawable enterShape = ResourcesCompat.getDrawable(getResources(), R.drawable.shape_droptarget, null);
        private final Drawable normalShape = ResourcesCompat.getDrawable(getResources(), R.drawable.shape, null);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    if (event.getLocalState() == null) break;

                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();

                    if (owner == null || owner == v) break;

                    owner.removeView(view);

                    if (v instanceof LinearLayout) {
                        LinearLayout container = (LinearLayout) v;

                        if (container.getChildCount() > 0) {
                            View oldView = container.getChildAt(0);
                            container.removeViewAt(0);

                            owner.addView(oldView);
                        }

                        container.addView(view);

                        if (checkAllImagesInPlace()) {
                            GridLayout gridLayout = findViewById(R.id.gridLayout);
                            gridLayout.removeAllViews();
                            showCompletedImage(gridLayout);

                        }
                    }
                    break;
                default:
                    break;
            }
            return true;
        }

        private boolean checkAllImagesInPlace() {
            GridLayout gridLayout = findViewById(R.id.gridLayout);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View childView = gridLayout.getChildAt(i);

                if (!(childView instanceof LinearLayout)) continue;

                LinearLayout linearLayout = (LinearLayout) childView;

                if (linearLayout.getChildCount() != 1) continue;

                View innerChildView = linearLayout.getChildAt(0);

                if (!(innerChildView instanceof CustomImageView)) continue;

                CustomImageView customImageView = (CustomImageView) innerChildView;

                Drawable currentDrawable = customImageView.getDrawable();

                int row = i / 3;
                int column = i % 3;

                int expectedResourceId = resourceIds[row][column];
                Drawable expectedDrawable = ResourcesCompat.getDrawable(getResources(), expectedResourceId, null);

                if (!areDrawablesEqual(currentDrawable, expectedDrawable)) {
                    return false;
                }
            }

            return true;
        }

        private boolean areDrawablesEqual(Drawable drawable1, Drawable drawable2) {
            if (drawable1 == null && drawable2 == null) return true;
            if (drawable1 == null || drawable2 == null) return false;

            Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
            Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();

            return bitmap1.sameAs(bitmap2);
        }

        private void showCompletedImage(GridLayout gridLayout) {
            // Cancel the timer to stop updates
            if (timer != null) {
                timer.cancel();
            }

            gridLayout.removeAllViews();
            gridLayout.setRowCount(1);
            gridLayout.setColumnCount(1);

            CustomImageView completedImageView = new CustomImageView(Action3.this);
            completedImageView.setImageResource(R.drawable.action3_bridge_of_light);
            completedImageView.setAdjustViewBounds(true);

            // Create GridLayout.LayoutParams for centering the image
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f), // Center row
                    GridLayout.spec(GridLayout.UNDEFINED, 1f)  // Center column
            );
            params.width = GridLayout.LayoutParams.MATCH_PARENT;
            params.height = GridLayout.LayoutParams.MATCH_PARENT;
            params.setGravity(Gravity.CENTER); // Center within the grid
            completedImageView.setLayoutParams(params);

            // Add the completed image to the GridLayout
            gridLayout.addView(completedImageView);

            btnNext.setVisibility(View.VISIBLE);

            // Display a toast message
            Toast.makeText(Action3.this, getString(R.string.action3_notMatch), Toast.LENGTH_LONG).show();
        }
    }

    public static class CustomImageView extends AppCompatImageView {
        public CustomImageView(Context context) {
            super(context);
        }

        public CustomImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean performClick() {
            // Provide custom click behavior if needed
            return super.performClick();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel(); // Pause the timer
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

}