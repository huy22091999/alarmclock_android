package com.xecongnong.oclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import java.util.*

class Alarm(
    var alarmId: Int,
    val hour: Int,
    val minute: Int,
    var isStarted: Boolean,
    val isRecurring: Boolean,

) {

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        if (!isRecurring) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    alarmPendingIntent
                )
            }
        }
        isStarted = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            alarmId, intent, 0
        )
        alarmManager.cancel(alarmPendingIntent)
        isStarted = false

        val toastText = String.format(
            "Alarm cancelled for %02d:%02d with id %d",
            hour,
            minute,
            alarmId
        )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }


}
