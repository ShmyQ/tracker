package com.example.williamschymik.tracker.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.williamschymik.tracker.AppStartReceiver;
import com.example.williamschymik.tracker.DataLogHandler;
import com.example.williamschymik.tracker.DataLog;
import com.example.williamschymik.tracker.R;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        mReceiver = new AppStartReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(mReceiver, filter);
    }

    public void setMood(View view) {
        String mood = view.getTag().toString();
        final DataLogHandler myDb = DataLogHandler.getInstance(this);
        myDb.insertDataLog(new DataLog("mood", mood));
    }

    public void setEnergy(View view) {
        String energy = view.getTag().toString();
        final DataLogHandler myDb = DataLogHandler.getInstance(this);
        myDb.insertDataLog(new DataLog("energy", energy));
    }

    public void setBody(View view) {
        String body = view.getTag().toString();
        final DataLogHandler myDb = DataLogHandler.getInstance(this);
        myDb.insertDataLog(new DataLog("body", body));
    }

    public void viewSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void viewDataCollector(View view) {
        Intent intent = new Intent(this, DataPageActivity.class);
        intent.putExtra("DATA_PAGE_NAME", (String) view.getTag());
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void exitProgram(View view) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}