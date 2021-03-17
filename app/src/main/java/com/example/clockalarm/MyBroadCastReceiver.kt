package com.example.clockalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.clockalarm.util.getNotification

class MyBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.getNotification("hiii", "this is alram")
        /* val builder=  NotificationCompat.Builder(context!!, "notify")
              .setSmallIcon(R.drawable.ic_baseline_alarm_24)
              .setContentTitle("Remind")
              .setContentText("Vipin")
              .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager= NotificationManagerCompat.from(context)
          notificationManager.notify(200,builder.build())*/
    }
}