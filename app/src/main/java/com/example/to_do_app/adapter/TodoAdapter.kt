package com.example.to_do_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.databinding.TodoItemBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    var toDoList = emptyList<ToDoModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        return TodoHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    override fun getItemCount(): Int {
        return if (toDoList.isNotEmpty())
            toDoList.size
        else 0
    }

    class TodoHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoModel: ToDoModel) {
            binding.toDo = toDoModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TodoHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodoItemBinding.inflate(layoutInflater, parent, false)
                return TodoHolder(binding)
            }
        }

    }
}