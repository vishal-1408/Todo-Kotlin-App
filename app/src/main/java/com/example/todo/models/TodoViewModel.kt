package com.example.todo.models

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.database.Task.Status
import com.example.todo.database.Task.Task
import com.example.todo.database.Task.TaskDao
import com.example.todo.receivers.NotificationReceiver
import kotlinx.coroutines.flow.Flow

const val NOTIFICATION_TAG = "NOTIFICATION_TAG"
const val MESSAGE = "MESSAGE"

class TodoViewModel(private val taskDao: TaskDao) : ViewModel() {

    fun insertData(
        title: String,
        desc: String,
        status: Status,
        notificationTime: Long,
        context: Context
    ) {
        val task = Task(title = title, description = desc, status = status)
        val id = taskDao.insert(task)
        Log.i("INSERT", id.toString())
        val message = """START THE TASK: ${task.title}!
            Don't be lazy!""".trimIndent()
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NOTIFICATION_TAG, id)
        intent.putExtra(MESSAGE, message)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            pendingIntent
        )
//        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.sendNotification(context,id.toInt(),message)
    }

    fun updateData(task: Task) = taskDao.update(task)
    fun getByStatus(status: Status): Flow<List<Task>> = taskDao.getByStatus(status)
    fun deleteTask(task: Task) = taskDao.deleteTask(task)


    fun clearAlarm(task: Task, context: Context) {
        val message = """START THE TASK: ${task.title}!
            Don't be lazy!""".trimIndent()
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NOTIFICATION_TAG, task.id)
        intent.putExtra(MESSAGE, message)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
//        AlarmManagerCompat.setExactAndAllowWhileIdle(
//            alarmManager,
//            AlarmManager.RTC_WAKEUP,
//            notificationTime,
//            pendingIntent
//        )
    }


}

//make a viewModelFactory for above viewModel

class TodoViewModelFactory(private val taskDao:TaskDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Invalid View Model Class")
    }
}