package com.example.to_do_app.data.repository

import androidx.lifecycle.LiveData
import com.example.to_do_app.data.db.ToDoDao
import com.example.to_do_app.data.models.ToDoModel

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoModel>> = toDoDao.getAllData()

    suspend fun insert(toDoModel: ToDoModel) {
        toDoDao.insertData(toDoModel)
    }

    suspend fun updateToDo(toDoModel: ToDoModel) {
        toDoDao.updateToDo(toDoModel)
    }

    suspend fun deleteToDO(id: ToDoModel) {
        toDoDao.deleteToDO(id)
    }

    suspend fun deleteAllToDos() {
        toDoDao.deleteAllToDos()
    }

    fun searchToDo(searchQuery: String): LiveData<List<ToDoModel>> =
        toDoDao.searchDataBase(searchQuery)
}