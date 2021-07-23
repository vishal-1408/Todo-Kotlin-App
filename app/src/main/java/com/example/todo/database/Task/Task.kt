package com.example.todo.database.Task

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

enum class Status{
    AVAILABLE,STARTED,COMPLETED
}

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @NotNull val title:String,
    @NotNull val description:String,
    @NotNull val status:Status
)