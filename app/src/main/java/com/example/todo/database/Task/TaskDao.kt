package com.example.todo.database.Task

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    //insert data
    @Query("Insert into Task(title,description,status) values(:title,:desc,:status)")
    fun insert(title: String, desc: String, status: Status)

    //update status
    @Update
    fun update(task: Task)

    //query by status
    @Query("Select * from Task where status= :status")
    fun getByStatus(status: Status): Flow<List<Task>>

    @Delete
    fun deleteTask(task: Task)
}