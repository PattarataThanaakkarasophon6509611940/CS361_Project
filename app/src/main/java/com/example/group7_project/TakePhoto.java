package com.example.group7_project;

import static com.example.group7_project.Constants.BACK_PRESS_INTERVAL;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.OutputStream;

public class TakePhoto extends Activity {
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

        // รับค่าสีจาก Bad ending
        color = getIntent().getStringExtra("color");

        imageView = findViewById(R.id.imageView);
        Button captureButton = findViewById(R.id.select_img);
        Button saveButton = findViewById(R.id.button1);

        // ถ่ายภาพ
        captureButton.setOnClickListener(v -> dispatchTakePictureIntent());
        // ตรวจสอบสิทธิ์การใช้กล้อง
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 101);
        }

        //บันทึกภาพ
        saveButton.setOnClickListener(v -> {
            if (capturedImage != null) {
                saveImageToDatabase("ไม่มีชื่อเรื่อง", capturedImage); // ใช้ค่าเริ่มต้นสำหรับชื่อเรื่อง
            } else {
                Toast.makeText(TakePhoto.this, "กรุณาถ่ายภาพก่อนบันทึก", Toast.LENGTH_SHORT).show();
            }
        });
    }//end onCreate
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                capturedImage = (Bitmap) data.getExtras().get("data");
                if (capturedImage != null) {
                    imageView.setImageBitmap(capturedImage);
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
    // บันทึกภาพลงฐานข้อมูล SQLite
    private void saveImageToDatabase(String title, Bitmap image) {
        ContentResolver resolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_" + System.currentTimeMillis() + ".jpg");
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES); // ต้องการสำหรับ Android Q+

        Uri imageUri = null;
        OutputStream outputStream = null;

        try {
            // เพิ่ม Log ก่อนสร้าง URI
            Log.d("SaveImage", "เริ่มการบันทึกภาพ...");

            // สร้าง URI สำหรับบันทึก
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            // เพิ่ม Log หลังสร้าง URI
            Log.d("SaveImage", "Image URI: " + imageUri);

            if (imageUri == null) {
                Log.e("SaveImage", "ไม่สามารถสร้าง URI ได้");
                Toast.makeText(this, "ไม่สามารถบันทึกภาพได้", Toast.LENGTH_SHORT).show();
                return;
            }

            // เปิด OutputStream สำหรับเขียนข้อมูล
            outputStream = resolver.openOutputStream(imageUri);
            if (outputStream == null) {
                Log.e("SaveImage", "ไม่สามารถเปิด OutputStream ได้");
                Toast.makeText(this, "เกิดข้อผิดพลาดในการบันทึกภาพ", Toast.LENGTH_SHORT).show();
                return;
            }

            // บันทึกภาพด้วยความละเอียดสูง
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            // Log หลังบันทึกสำเร็จ
            Log.d("SaveImage", "บันทึกภาพสำเร็จใน Gallery");
            Toast.makeText(this, "บันทึกภาพลง Gallery สำเร็จ", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("SaveImage", "Error: " + e.getMessage());
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
}