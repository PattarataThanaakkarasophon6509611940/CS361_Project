package com.example.group7_project;

import static android.provider.BaseColumns._ID;
import static com.example.group7_project.Constants.IMAGE;
import static com.example.group7_project.Constants.TABLE_NAME;
import static com.example.group7_project.Constants.TIME;
import static com.example.group7_project.Constants.TITLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper {
    public EventsData(Context ctx){
        super(ctx, "events.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIME + " INTEGER, "
                + TITLE + " TEXT NOT NULL, "
                + IMAGE + " TEXT NOT NULL);"  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
}