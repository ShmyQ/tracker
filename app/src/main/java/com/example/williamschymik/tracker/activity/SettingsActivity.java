package com.example.williamschymik.tracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    public void getData(View view) {
        final DataLogHandler myDb = DataLogHandler.getInstance(this);
        myDb.getDataLogs();
    }

    public void resetData(View view) {
        final DataLogHandler myDb = DataLogHandler.getInstance(this);
        myDb.resetDataLogs();
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}