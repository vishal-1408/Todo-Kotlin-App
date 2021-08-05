package com.example.todo.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todo.MainActivity
import com.example.todo.R


fun NotificationManager.sendNotification(context: Context, notificationTag: Int, message: String) {

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        -1 * notificationTag,
        intent,
        PendingIntent.FLAG_ONE_SHOT
    )
    val notification = NotificationCompat.Builder(
        context,
        context.getString(R.string.channel_name)
    ).setContentTitle(context.getString(R.string.start_task))
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_baseline_assignment_24)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notify(notificationTag, notification)
}

