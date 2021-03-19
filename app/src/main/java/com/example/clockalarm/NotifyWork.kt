package com.example.clockalarm

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.*
import com.example.clockalarm.util.getNotification
import java.sql.Time
import java.util.concurrent.TimeUnit

class NotifyWork(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        applicationContext.getNotification("Reminder", "Please get Up")

        val interval = applicationContext.getSharedPreferences("interval", Context.MODE_PRIVATE)
        val intervalData =interval.getLong("intervalData",0)
        val endTime =interval.getLong("endTime",0)
        val eHour = TimeUnit.MILLISECONDS.toHours(endTime)
        val eMin = TimeUnit.MILLISECONDS.toMinutes(endTime)

        Log.d("==>me", "$intervalData,$endTime")

        if (TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis()) == eHour && TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()) == eMin)
        {
            WorkManager.getInstance(applicationContext).cancelAllWorkByTag("startTag")
            Log.d("==>", "Break")
        }else
        {
            val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
                .setInitialDelay(intervalData * 60 * 1000, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(applicationContext).enqueue(notificationWork)
        }

        return Result.success()
    }
}