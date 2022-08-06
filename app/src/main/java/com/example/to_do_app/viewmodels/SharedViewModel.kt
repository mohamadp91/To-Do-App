package com.example.to_do_app.viewmodels

import android.app.Application
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.to_do_app.R
import com.example.to_do_app.data.models.Priority
import com.example.to_do_app.data.models.ToDoModel
import com.google.android.material.textfield.TextInputLayout

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val items = listOf("High Priority", "Medium Priority", "Low Priority")

    val isDatabaseEmpty = MutableLiveData<Boolean>(true)

    fun checkIsDatabaseEmpty(toDoList : List<ToDoModel>){
        isDatabaseEmpty.value = toDoList.isEmpty()
    }

    fun parsePriority(str: String): Priority {
        return when (str) {
            "High Priority" -> Priority.High
            "Medium Priority" -> Priority.Medium
            else -> Priority.Low
        }
    }

    fun verifyInputs(vararg inputs: EditText): Boolean {
        return inputs.all {
            hasError(it)
        }
    }

    private fun hasError(input: EditText): Boolean {
        return if (!input.text?.trim().isNullOrEmpty()) {
            input.error = null
            true
        } else {
            input.error = "Please enter correct value for ${input.hint}"
            false
        }
    }

    infix fun getValue(input: EditText): String = input.text.toString()

}