package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;

public class TakePhoto extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private Bitmap capturedImage;
    private EventsData eventsData;
    private DatabaseHelper dbHelper;
    private String color;
    private long backPressedTime =0;
    private int sceneIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        eventsData = new EventsData(this);
        dbHelper = new DatabaseHelper(this);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
        }

        // ตรวจสอบสิทธิ์ WRITE_EXTERNAL_STORAGE สำหรับ Android 10 และต่ำกว่า
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102);
        }

        // Set status bar color to black
        getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        // Set navigation bar color to black
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, android.R.color.black));

        color = getIntent().getStringExtra("color");

        imageView = findViewById(R.id.imageView);
        Button captureButton = findViewById(R.id.select_img);
        Button saveButton = findViewById(R.id.saveImage);
        Button backToMain = findViewById(R.id.backToMain);
        ImageView frameView = findViewById(R.id.frameView);

        // ถ่ายภาพ
        captureButton.setOnClickListener(v -> dispatchTakePictureIntent());
        // ตรวจสอบสิทธิ์การใช้กล้อง
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 101);
        }

        //บันทึกภาพ
        saveButton.setOnClickListener(v -> {
            if (capturedImage != null) {
                Bitmap combinedImage = combineImages(capturedImage, frameView);
                if (combinedImage != null) {
                    saveImageToGallery(combinedImage);
                } else {
                    Toast.makeText(this, "ไม่สามารถรวมภาพได้", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "กรุณาถ่ายภาพก่อนบันทึก", Toast.LENGTH_SHORT).show();
            }
        });
        backToMain.setOnClickListener(v -> {
            Intent intent = new Intent(TakePhoto.this, MainActivity.class);
            intent.putExtra("color", color);
            startActivity(intent);
            finish();
        });

    }//end onCreate
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    // รวมภาพถ่ายกับกรอบรูป
    private Bitmap combineImages(Bitmap capturedImage, ImageView frameView) {
        try {
            // รับ Bitmap ของกรอบรูป
            frameView.setDrawingCacheEnabled(true);
            Bitmap frameBitmap = Bitmap.createBitmap(frameView.getDrawingCache());
            frameView.setDrawingCacheEnabled(false);

            // สร้าง Bitmap ใหม่ที่รวมภาพ
            Bitmap result = Bitmap.createBitmap(frameBitmap.getWidth(), frameBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            // วาดภาพถ่ายบน Bitmap ใหม่
            Rect src = new Rect(0, 0, capturedImage.getWidth(), capturedImage.getHeight());
            Rect dest = new Rect(0, 0, frameBitmap.getWidth(), frameBitmap.getHeight());
            canvas.drawBitmap(capturedImage, src, dest, null);

            // วาดกรอบรูปทับบน Bitmap ใหม่
            canvas.drawBitmap(frameBitmap, 0, 0, null);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                capturedImage = (Bitmap) data.getExtras().get("data");
                if (capturedImage != null) {
                    Bitmap squareImage = cropToSquare(capturedImage);
                    scaleAndDisplayImage(capturedImage);
                    Log.d("IMAGE_CAPTURE", "ถ่ายภาพสำเร็จและแสดงผลใน ImageView");
                } else {
                    Log.e("IMAGE_CAPTURE", "ภาพที่ได้เป็น null");
                    Toast.makeText(this, "เกิดข้อผิดพลาดในการรับภาพ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("IMAGE_CAPTURE", "Intent data เป็น null");
                Toast.makeText(this, "ไม่สามารถรับข้อมูลภาพได้", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("IMAGE_CAPTURE", "ไม่สามารถถ่ายภาพได้ หรือถูกยกเลิก");
        }
    }
    private Bitmap cropToSquare(Bitmap image) {
        // หาขนาดที่เล็กที่สุดระหว่างความกว้างและความสูง
        int width = image.getWidth();
        int height = image.getHeight();
        int size = Math.min(width, height); // ขนาดสี่เหลี่ยมจัตุรัสที่เราจะตัด

        // คำนวณตำแหน่งเริ่มต้น (left, top) สำหรับการตัด
        int x = (width - size) / 1;  // ตำแหน่งเริ่มต้นที่คำนวณจากขอบซ้าย
        int y = (height - size) / 1; // ตำแหน่งเริ่มต้นที่คำนวณจากขอบบน

        // ตัดภาพให้เป็นสี่เหลี่ยมจัตุรัส
        Bitmap squareBitmap = Bitmap.createBitmap(image, x, y, size, size);

        return squareBitmap;
    }

    // บันทึกภาพลง Gallery
    private void saveImageToGallery(Bitmap image) {
        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        Uri imageUri = null;
        OutputStream outputStream = null;

        try {
            // สร้าง URI สำหรับบันทึก
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            // ตรวจสอบว่า imageUri ไม่เป็น null
            if (imageUri != null) {
                // เปิด OutputStream สำหรับเขียนข้อมูลไปที่ URI
                outputStream = resolver.openOutputStream(imageUri);

                // ตรวจสอบว่า OutputStream ไม่เป็น null
                if (outputStream != null) {
                    // บันทึกภาพลงใน OutputStream
                    image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    Toast.makeText(this, R.string.saveImageCompleted, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "ไม่สามารถเปิด OutputStream ได้", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "ไม่สามารถสร้าง URI ได้", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "เกิดข้อผิดพลาด: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            // ปิด OutputStream เพื่อป้องกันการรั่วไหลของทรัพยากร
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        dbHelper.saveLastSubscene("TakePhoto", sceneIndex,null,color);
        getSharedPreferences("app_prefs", MODE_PRIVATE).edit()
                .putString("last_scene", "Action1")
                .putInt("last_subscene", sceneIndex)
                .apply();
        dbHelper.close();
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime < BACK_PRESS_INTERVAL) {
            dbHelper.saveLastSubscene("TakePhoto", sceneIndex, null, color);
            finishAffinity();
        } else {
            // กดครั้งแรก แสดง Toast แจ้งเตือน
            Toast.makeText(this, R.string.back, Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        }
    }
    // ฟังก์ชันสำหรับปรับขนาดภาพ
    private void scaleAndDisplayImage(Bitmap image) {
        // ดึงขนาดของ ImageView ที่ใช้เป็นกรอบ
        ImageView imageView = findViewById(R.id.imageView);
        int width = imageView.getWidth();
        int height = imageView.getHeight();

        // ปรับขนาดภาพให้เข้ากับกรอบ
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(image, width, height, true);

        // แสดงภาพที่ปรับขนาดแล้วใน ImageView
        imageView.setImageBitmap(scaledBitmap);
    }
}