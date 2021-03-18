package com.example.clockalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.clockalarm.util.getNotification

class MyBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.getNotification("hello", "please get up")
    }
}





















/*
//for ringtone
Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
if (alarmUri == null)
{
    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
}
Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
ringtone.play();*/
