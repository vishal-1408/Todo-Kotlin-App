package com.example.todo.database.Task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {
    //insert data
    @Query("Insert into Task(title,description,status) values(:title,:desc,:status)")
    fun insert(title:String,desc:String,status:Status):Unit

    //update status
    @Update
    fun update(task:Task):Unit

    //query by status
    @Query("Select * from Task where status= :status")
    fun getByStatus(status:Status):List<Task>
}