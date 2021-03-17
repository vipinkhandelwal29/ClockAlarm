package com.example.clockalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.clockalarm.databinding.ActivityMainBinding
import java.util.*


class MainActivity : BaseActivity<ActivityMainBinding>(), AdapterView.OnItemSelectedListener {
    override fun getLayoutId() = R.layout.activity_main

    override fun initControl() {

        var timePicker: TimePickerDialog
        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)


        val adapterBG = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.Timer,
            android.R.layout.simple_spinner_item
        )
        adapterBG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterBG
        binding.spinner.onItemSelectedListener = this


        binding.tvTime1.setOnClickListener {
            timePicker = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    binding.tvTime1.text = String.format("%d : %d", hourOfDay, minute)
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
        }
        binding.btnSetAlarm.setOnClickListener {
            val alarmManager =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, MyBroadCastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            alarmManager.set(AlarmManager.RTC_WAKEUP, 1615988572000, pendingIntent)
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent  = Intent(this,MyBroadCastReceiver::class.java)
        val pIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.set(AlarmManager.RTC_WAKEUP,1615989600000,pIntent)

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
}
