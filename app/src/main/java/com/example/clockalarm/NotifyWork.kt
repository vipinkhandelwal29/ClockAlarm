package com.example.clockalarm

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.clockalarm.util.getNotification

class NotifyWork(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        applicationContext.getNotification("Reminder", "Please get Up")
        return Result.success()
    }
}