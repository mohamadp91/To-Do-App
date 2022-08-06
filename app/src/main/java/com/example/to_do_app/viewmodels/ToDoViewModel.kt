package com.example.to_do_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.to_do_app.data.db.ToDoDataBase
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDataBase.getDatabase(application.applicationContext).toDoDao()

    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    val getAllData: LiveData<List<ToDoModel>> = repository.getAllData

    fun insertData(toDoModel: ToDoModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(toDoModel)
        }
    }

    fun updateToDo(toDoModel: ToDoModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateToDo(toDoModel)
        }
    }
}