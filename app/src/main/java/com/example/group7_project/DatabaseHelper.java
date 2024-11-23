package com.example.group7_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "game_progress.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE progress (id INTEGER PRIMARY KEY, scene TEXT, subscene INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
    }

    // บันทึกสถานะล่าสุด
    public void saveLastScene(String scene, int subscene) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("scene", scene);
        values.put("subscene", subscene);
        db.insertWithOnConflict("progress", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // ดึงสถานะล่าสุด
    public Cursor getLastProgress() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM progress LIMIT 1", null);
    }
}