package com.example.to_do_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.to_do_app.data.models.ToDoModel

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_tb ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDo: ToDoModel)

    @Update
    suspend fun updateToDo(toDo: ToDoModel)

    @Delete
    suspend fun deleteToDO(id: ToDoModel)

    @Query("DELETE from todo_tb")
    suspend fun deleteAllToDos()

    @Query("SELECT * FROM todo_tb WHERE title LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): LiveData<List<ToDoModel>>


    @Query("SELECT * FROM todo_tb ORDER BY CASE WHEN priority LIKE 'H%' THEN 1  WHEN priority LIKE 'M%' THEN 2  WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHigh(): LiveData<List<ToDoModel>>


    @Query("SELECT * FROM todo_tb ORDER BY CASE WHEN priority LIKE 'L%' THEN 1  WHEN priority LIKE 'M%' THEN 2  WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLow(): LiveData<List<ToDoModel>>


}