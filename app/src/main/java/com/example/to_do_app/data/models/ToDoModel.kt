package com.example.to_do_app.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_tb")
@Parcelize
data class ToDoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val priority: Priority,
    val description: String
) : Parcelable