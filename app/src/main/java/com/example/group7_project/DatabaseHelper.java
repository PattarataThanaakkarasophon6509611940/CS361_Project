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
                "subscene INTEGER, " +
                "book TEXT DEFAULT NULL, " +
                "color TEXT DEFAULT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE progress ADD COLUMN endingType TEXT DEFAULT NULL");
        }
    }

    public void saveLastSubscene(String scene, int subscene, String book, String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("scene", scene);
        values.put("subscene", subscene);
        values.put("book", book);
        values.put("color", color);

        db.insert("progress", null, values);
    }

    public Cursor getLastSubscene() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("progress", null, null, null, null, null, "id DESC", "1");
    }
    public void printLastSubscene() {
        Cursor cursor = getLastSubscene();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String scene = cursor.getString(cursor.getColumnIndexOrThrow("scene"));
                int subscene = cursor.getInt(cursor.getColumnIndexOrThrow("subscene"));
                String book = cursor.getString(cursor.getColumnIndexOrThrow("book"));
                String color = cursor.getString(cursor.getColumnIndexOrThrow("color"));

                // พิมพ์ข้อมูล
                System.out.println("ID: " + id);
                System.out.println("Scene: " + scene);
                System.out.println("Subscene: " + subscene);
                System.out.println("Book: " + (book != null ? book : "null"));
                System.out.println("Color: " + (color != null ? color : "null"));
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            System.out.println("No data found in the database.");
        }
    }
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS progress");
        onCreate(db);
        System.out.println("Database has been reset.");
    }
}