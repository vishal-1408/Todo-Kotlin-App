package com.example.todo.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todo.models.MESSAGE
import com.example.todo.models.NOTIFICATION_TAG
import com.example.todo.utils.sendNotification

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("INSERT", "RECEIVED!!!!!")
        Log.i("insert", "commmonnnnnnn!!!!!")
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent?.extras?.getLong(NOTIFICATION_TAG)
        val message = intent?.extras?.getString(MESSAGE)
        notificationManager.sendNotification(context!!, notificationId!!.toInt(), message!!)
    }
}
