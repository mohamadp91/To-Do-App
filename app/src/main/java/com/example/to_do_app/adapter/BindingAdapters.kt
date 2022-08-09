package com.example.to_do_app.adapter

import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.to_do_app.R
import com.example.to_do_app.data.models.Priority
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDataBase")
        @JvmStatic
        fun emptyDataBase(view: View, isEmpty: MutableLiveData<Boolean>) {
            when (isEmpty.value) {
                true -> view.visibility = View.VISIBLE
                else -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:sendDataToUpdate")
        @JvmStatic
        fun sendDataToUpdate(view: View, toDoModel: ToDoModel) {
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(toDoModel)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:priorityColorIndicator")
        @JvmStatic
        fun priorityColorIndicator(view: CardView, priority: Priority) {
            return when (priority) {
                Priority.High -> view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.red
                    )
                )
                Priority.Medium -> view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.yellow
                    )
                )
                else -> view.setCardBackgroundColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
            }
        }
    }
}