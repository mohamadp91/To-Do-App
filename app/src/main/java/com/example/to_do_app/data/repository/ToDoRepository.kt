package com.example.to_do_app.data.repository

import androidx.lifecycle.LiveData
import com.example.to_do_app.data.db.ToDoDao
import com.example.to_do_app.data.models.ToDoModel
import kotlinx.coroutines.Dispatchers

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData : LiveData<List<ToDoModel>> = toDoDao.getAllData()

    suspend fun insert(toDoModel: ToDoModel){
        toDoDao.insertData(toDoModel)
    }

    suspend fun updateToDo(toDoModel: ToDoModel){
        toDoDao.updateToDo(toDoModel)
    }
}