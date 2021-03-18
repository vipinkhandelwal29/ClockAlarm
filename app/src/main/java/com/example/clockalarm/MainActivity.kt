package com.example.clockalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.clockalarm.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity<ActivityMainBinding>(), AdapterView.OnItemSelectedListener {
    private var interval: Long? = null
    private var fromTime: Long? = null
    private var toTime: Long? = null

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    override fun getLayoutId() = R.layout.activity_main

    override fun initControl() {

        var timePicker: TimePickerDialog
        val currentTime = Calendar.getInstance()
        val h = currentTime.get(Calendar.HOUR_OF_DAY)
        val m = currentTime.get(Calendar.MINUTE)


        val adapterBG = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.Timer,
            android.R.layout.simple_spinner_item
        )
        adapterBG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterBG
        binding.spinner.onItemSelectedListener = this


        /* binding.tvTime1.setOnClickListener {
             timePicker = TimePickerDialog(
                 this,
                 TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute->
                     binding.tvTime1.text = String.format("%d : %d :%d", hourOfDay, minute)
                 }, h, m, true
             )
             timePicker.show()
         }

         binding.tvTime2.setOnClickListener {

             timePicker = TimePickerDialog(
                 this,
                 TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                     binding.tvTime2.text = String.format("%d : %d", hourOfDay, minute)
                 }, h, m, true
             )
             timePicker.show()
         }*/


        binding.tvTime1.setOnClickListener {
            timePicker = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.tvTime1.text = String.format("%d : %d", hourOfDay, minute)
                    currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    currentTime.set(Calendar.MINUTE, minute)
                    currentTime.set(Calendar.SECOND, 0)
                    currentTime.set(Calendar.MILLISECOND, 0)
                    fromTime = currentTime.timeInMillis
                }, h, m, false
            )
            timePicker.show()
        }

        binding.tvTime2.setOnClickListener {
            timePicker = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.tvTime2.text = String.format("%d : %d", hourOfDay, minute)
                    currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    currentTime.set(Calendar.MINUTE, minute)
                    currentTime.set(Calendar.SECOND, 0)
                    currentTime.set(Calendar.MILLISECOND, 0)
                    toTime = currentTime.timeInMillis
                }, h, m, false
            )
            timePicker.show()
        }

        binding.btnSetAlarm.setOnClickListener {
            val time = interval!! * 60 * 1000

            val myPeriodicWorkRequest =
                PeriodicWorkRequestBuilder<NotifyWork>(time, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(this).enqueue(myPeriodicWorkRequest)

            /*val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
                .setInitialDelay(time, TimeUnit.MILLISECONDS).build()
            Log.d("==>ne", "$time,$notificationWork")

            WorkManager.getInstance(this).enqueue(notificationWork)*/

            /*  alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
              val intent = Intent(this, MyBroadCastReceiver::class.java)
              pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
              alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, fromTime!!, interval!! * 60 * 1000, pendingIntent)
              Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show()
              Log.d("==>", "$fromTime, $toTime, $interval")*/
        }
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        interval = parent!!.getItemAtPosition(position).toString().toLong()
    }
}
