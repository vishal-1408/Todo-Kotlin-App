package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.Task.Task
import com.example.todo.database.Task.TaskDao

@Database(entities = arrayOf(Task::class),version=1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object{
        private var INSTANCE:AppDatabase?=null
        fun getInstance(applicationContext:Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}