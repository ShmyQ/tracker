package com.example.williamschymik.tracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataLogHandler extends SQLiteOpenHelper {
    private static final String TAG = "DataLogHandler";
    public static final String DATABASE_NAME = "Tracker.db";

    public static synchronized DataLogHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataLogHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS data_log(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type VARCHAR," +
                "value VARCHAR," +
                "created_at TEXT);");
        db.execSQL("CREATE INDEX created_at_idx on data_log(created_at)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS data_log;");
        onCreate(db);
    }

    public void insertDataLog(DataLog dataLog) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(String.format("INSERT INTO data_log('type','value','created_at') VALUES('%s','%s',datetime('now', 'localtime'));",
                dataLog.getType(), dataLog.getValue()));
        db.close();
    }

    public Date getLastDataEntry() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from data_log ORDER BY id DESC LIMIT 1", null);
        Date date = null;
        if (cursor.moveToFirst()) {
            Log.i(TAG, cursor.getString(cursor.getColumnIndex("type")));
            Log.i(TAG, cursor.getString(cursor.getColumnIndex("value")));
            String createdString = cursor.getString(cursor.getColumnIndex("created_at"));
            Log.i(TAG, createdString);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                date = format.parse(createdString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();

        return date;
    }

    public void getDataLogs() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from data_log", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(TAG, cursor.getString(cursor.getColumnIndex("type")));
                Log.i(TAG, cursor.getString(cursor.getColumnIndex("value")));
                Log.i(TAG, cursor.getString(cursor.getColumnIndex("created_at")));

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
    }

    public void resetDataLogs() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM data_log;");
        db.close();
    }

    private static DataLogHandler sInstance;

    private DataLogHandler(Context context) {
        super(context, DATABASE_NAME , null, 7);
    }
}
