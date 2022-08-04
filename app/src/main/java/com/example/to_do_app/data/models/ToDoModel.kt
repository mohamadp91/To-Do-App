package com.example.to_do_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_tb")
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String
)