package com.example.williamschymik.tracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppStartReceiver extends BroadcastReceiver {
    private static final String TAG = "AppStartReceiver";

    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Got context " + intent.getAction());

        final DataLogHandler myDb = DataLogHandler.getInstance(context);
        Date lastEntry = myDb.getLastDataEntry();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -3);

        // Only prompt if data has not been entered in 3 hours
        if (lastEntry == null || cal.getTime().getTime() > lastEntry.getTime()) {
            ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> rt = am.getAppTasks();
            for (ActivityManager.AppTask task : rt) {
                if (task.getTaskInfo().baseActivity.toShortString().contains("tracker")) {
                    am.moveTaskToFront(task.getTaskInfo().id, ActivityManager.MOVE_TASK_WITH_HOME);
                }
            }
        }
    }
}
