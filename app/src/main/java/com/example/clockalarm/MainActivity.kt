package com.example.clockalarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.clockalarm.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity<ActivityMainBinding>() {


    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun getLayoutId() = R.layout.activity_main

    override fun initControl() {
        var interval: Long? = null
        val fromTime: Calendar = Calendar.getInstance()
        val toTime: Calendar = Calendar.getInstance()

        val adapter = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.Timer,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                interval = parent!!.getItemAtPosition(position).toString().toLong()
            }
        }
        val pref = this.getSharedPreferences("interval",Context.MODE_PRIVATE)



        setTime(binding.tvTime1, fromTime)
        setTime(binding.tvTime2, toTime)

        binding.btnSetAlarm.setOnClickListener {

            /*
                        val oneTimeWorkRequest =
                            PeriodicWorkRequestBuilder<NotifyWork>(time, TimeUnit.MILLISECONDS).build()
                        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                            "Vipin",
                            ExistingPeriodicWorkPolicy.KEEP,
                            myPeriodicWorkRequest)
                        */
            val editor = pref.edit().putLong("intervalData",binding.spinner.selectedItem.toString().toLong()).commit()
            val editor1=  pref.edit().putLong("endTime", toTime.timeInMillis).commit()
            val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java).addTag("startTag")
                .setInitialDelay(
                    (fromTime.timeInMillis - System.currentTimeMillis()), TimeUnit.MILLISECONDS).build()
            Log.d("==>",  "$notificationWork")
            WorkManager.getInstance(this).enqueue(notificationWork)

            /*  alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
              val intent = Intent(this, MyBroadCastReceiver::class.java)
              pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
              alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, fromTime!!, interval!! * 60 * 1000, pendingIntent)
              Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show()
              Log.d("==>", "$fromTime, $toTime, $interval")*/
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTime(textView: TextView, calendar: Calendar ) {
        textView.setOnClickListener {
            TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    textView.text =
                        SimpleDateFormat("hh:mm aa").format(Date(calendar.timeInMillis))
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false
            ).show()
        }
    }
}
