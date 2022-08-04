package com.example.to_do_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.to_do_app.data.models.ToDoModel

@Database(entities = [ToDoModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ToDoDataBase : RoomDatabase() {

    abstract fun toDoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: ToDoDataBase? = null

        fun getDatabase(context: Context): ToDoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ToDoDataBase::class.java,
                    "todo_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}