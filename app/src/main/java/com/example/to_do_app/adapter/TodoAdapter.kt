package com.example.to_do_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.R
import com.example.to_do_app.data.models.Priority
import com.example.to_do_app.data.models.ToDoModel
import com.google.android.material.textview.MaterialTextView

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    var toDoList = emptyList<ToDoModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoHolder(view)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {

        val toDo = toDoList[position]
        holder.apply {
            titleTxt.text = toDo.title
            descriptionTxt.text = toDo.description
            colorIndicator(toDo.priority, this)
        }

    }

    private fun colorIndicator(priority: Priority, holder: TodoHolder) {
        return when (priority) {
            Priority.High -> setPriorityColor(holder, R.color.red)
            Priority.Medium -> setPriorityColor(holder, R.color.yellow)
            else -> setPriorityColor(holder, R.color.green)
        }
    }

    private fun setPriorityColor(holder: TodoHolder, color: Int) {
        holder.priorityIndicator.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                color
            )
        )
    }

    override fun getItemCount(): Int {
        return if (toDoList.isNotEmpty())
            toDoList.size
        else 0
    }

    inner class TodoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTxt = itemView.findViewById<MaterialTextView>(R.id.title_txt)!!
        val priorityIndicator = itemView.findViewById<CardView>(R.id.priority_indicator)!!
        val descriptionTxt = itemView.findViewById<MaterialTextView>(R.id.description_txt)!!
    }
}