package com.example.to_do_app.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.to_do_app.R
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.viewmodels.SharedViewModel
import com.example.to_do_app.viewmodels.ToDoViewModel
import com.google.android.material.textfield.TextInputLayout

class AddFragment : Fragment(), MenuProvider {

    private lateinit var titleInput: TextInputLayout
    private lateinit var priorityInput: TextInputLayout
    private lateinit var descriptionInput: TextInputLayout

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val todoViewModel: ToDoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        configMenu()

        configPriorityList(view)

        findViewsById(view)

        return view
    }

    private fun configMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configPriorityList(view: View) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, sharedViewModel.items)

        view.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
            ?.apply {
                setAdapter(adapter)
        }

    }

    private fun findViewsById(view: View) {
        titleInput = view.findViewById(R.id.todoTitle)
        priorityInput = view.findViewById(R.id.priorityMenu)
        descriptionInput = view.findViewById(R.id.description)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.submit -> {
                if (sharedViewModel.verifyInputs(titleInput, priorityInput, descriptionInput)) {
                    saveToDO()
                    findNavController().navigate(R.id.action_addFragment_to_listFragment)
                    return true
                }
            }
        }
        return false
    }

    private fun saveToDO() {
        val todo = ToDoModel(
            0,
            sharedViewModel getValue titleInput,
            sharedViewModel.parsePriority(sharedViewModel getValue priorityInput),
            sharedViewModel getValue descriptionInput
        )
        todoViewModel.insertData(todo)
    }

}
