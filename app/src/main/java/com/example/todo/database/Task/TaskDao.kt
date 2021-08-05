package com.example.todo.database.Task

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    //insert data
    @Insert
    fun insert(task: Task): Long

    //update status
    @Update
    fun update(task: Task)

    //query by status
    @Query("Select * from Task where status= :status")
    fun getByStatus(status: Status): Flow<List<Task>>

    @Delete
    fun deleteTask(task: Task)
}