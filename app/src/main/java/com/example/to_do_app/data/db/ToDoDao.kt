package com.example.to_do_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.to_do_app.data.models.ToDoModel

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_tb ORDER BY id ASC")
    fun getAllData() : LiveData<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDo: ToDoModel)
}