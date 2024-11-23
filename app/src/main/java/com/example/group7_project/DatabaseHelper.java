package com.example.group7_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game_progress.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE progress (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "scene TEXT, " +
                "subscene INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
    }

    public void saveLastSubscene(String scene, int subscene) {
        SQLiteDatabase db = this.getWritableDatabase();

        // บันทึกข้อมูลใหม่
        ContentValues values = new ContentValues();
        values.put("scene", scene);
        values.put("subscene", subscene);
        db.insert("progress", null, values);

    }

    public Cursor getLastSubscene() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("progress", new String[]{"scene", "subscene"},
                null, null, null, null, "id DESC", "1");
    }
}