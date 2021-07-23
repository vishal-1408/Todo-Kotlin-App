package com.example.todo

import android.app.Application
import com.example.todo.database.AppDatabase

class TodoApplication:Application() {
    val database:AppDatabase by lazy {
        AppDatabase.getInstance(applicationContext)
    }
}