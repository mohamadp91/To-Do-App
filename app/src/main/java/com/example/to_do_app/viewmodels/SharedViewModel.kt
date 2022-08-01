package com.example.to_do_app.viewmodels

import androidx.lifecycle.ViewModel
import com.example.to_do_app.data.models.Priority

class SharedViewModel : ViewModel() {

    fun parsePriority(str: String) : Priority {
        return when(str){
            "High Priority" -> Priority.High
            "Medium Priority" -> Priority.Medium
            else -> Priority.Low
        }
    }
}