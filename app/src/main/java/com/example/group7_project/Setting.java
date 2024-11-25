package com.example.group7_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import kotlinx.coroutines.channels.Send;


public class Setting {
    private Activity activity;

    public Setting(Activity activity) {
        this.activity = activity;
    }

    // ฟังก์ชันในการแสดง Modal
    public void showDialog(String scene,int currentIndex,String book,String color) {
        // สร้าง Dialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // ใช้ LayoutInflater เพื่อสร้าง Layout ที่ต้องการ
        View modalView = activity.getLayoutInflater().inflate(R.layout.modal_setting, null);

        // กำหนด Layout ให้กับ Dialog
        builder.setView(modalView);

        // สร้าง Dialog
        AlertDialog dialog = builder.create();

        // ป้องกันการปิด Dialog ด้วยการกดพื้นที่ข้างนอก
        dialog.setCancelable(false); // ปิดการกดปุ่ม Back
        dialog.setCanceledOnTouchOutside(false); // ปิดการแตะนอกพื้นที่ Dialog

        // หา Button จาก Layout ของ Modal
        Button btnResume = modalView.findViewById(R.id.btnResume);
        Button btnSaveAndExit = modalView.findViewById(R.id.btnSaveAndExit);

        // ตั้งค่าการคลิกปุ่ม Resume
        btnResume.setOnClickListener(v -> {
            dialog.dismiss(); // ปิด Dialog เมื่อคลิก Resume
        });

        // ตั้งค่าการคลิกปุ่ม Save and Exit
        btnSaveAndExit.setOnClickListener(v -> {
            saveAndExit(scene,currentIndex,book,color); // บันทึกข้อมูลและออกจากเกม
            dialog.dismiss(); // ปิด Dialog เมื่อคลิก Save and Exit
        });
        dialog.show();
    }

    private void saveAndExit(String scene,int currentSubscene,String book,String color) {
        DatabaseHelper dbHelper = new DatabaseHelper(activity);
        dbHelper.saveLastSubscene(scene, currentSubscene,book,color);
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        dbHelper.close();
    }
}