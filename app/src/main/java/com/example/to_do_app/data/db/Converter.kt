package com.example.to_do_app.data.db

import androidx.room.TypeConverter
import com.example.to_do_app.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(priority: String): Priority = Priority.valueOf(priority)
}