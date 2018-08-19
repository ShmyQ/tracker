package com.example.williamschymik.tracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataPageHandler extends SQLiteOpenHelper {
    private static final String TAG = "DataPageHandler";
    public static final String DATABASE_NAME = "Tracker.db";

    public static synchronized DataPageHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataPageHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS data_pages(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS data_page_components(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data_page_id INTEGER," +
                "position INTEGER," +
                "title VARCHAR," +
                "type VARCHAR," +
                "value VARCHAR);");

        db.execSQL(String.format("INSERT INTO data_pages VALUES" +
                "(1,'Vitamins')," +
                "(2,'Intoxicants')," +
                "(3,'Acne')"));
        db.execSQL("INSERT INTO data_page_components VALUES" +
                "(1,1,1,'Vitamins','options','[\"Vitamin D\", \"Coline\", \"Bacopa\",\"Claratin\"]')," +
                "(2,2,1,'Alcoholic Drinks','numeric','10')," +
                "(3,3,1,'Acne Levels','numeric','5')," +
                "(4,3,1,'Medication','options','[\"Benzoyle Peroxide 3%\", \"Benzoyle Peroxide 5%\", \"Clindamycin\"]')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS data_pages;");
        db.execSQL("DROP TABLE IF EXISTS data_page_components;");
        onCreate(db);
    }

    public DataPage getDataPage(String name) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * from data_pages WHERE name = '%s';", name), null);
        int page_id = 0;
        if (cursor.moveToFirst()) {
            page_id = cursor.getInt(cursor.getColumnIndex("id"));
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        List<DataPage.DataPageComponent> components = new ArrayList<>();
        cursor = db.rawQuery(String.format("SELECT * from data_page_components WHERE data_page_id = %d;", page_id), null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int position = cursor.getInt(cursor.getColumnIndex("position"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                components.add(new DataPage.DataPageComponent(position, title, type, value));

                cursor.moveToNext();
            }
        }

        db.close();

        return new DataPage(name, components);
    }

    private static DataPageHandler sInstance;

    private DataPageHandler(Context context) {
        super(context, DATABASE_NAME , null, 7);
    }
}
