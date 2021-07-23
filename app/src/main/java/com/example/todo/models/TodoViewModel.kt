package com.example.todo.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.database.Task.Status
import com.example.todo.database.Task.Task
import com.example.todo.database.Task.TaskDao
import java.lang.IllegalArgumentException

class TodoViewModel(private val taskDao: TaskDao): ViewModel() {

    fun insertData(title:String,desc:String,status:Status) = taskDao.insert(title,desc,status)
    fun updateData(task:Task) = taskDao.update(task)
    fun getByStatus(status:Status):List<Task> = taskDao.getByStatus(status)
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