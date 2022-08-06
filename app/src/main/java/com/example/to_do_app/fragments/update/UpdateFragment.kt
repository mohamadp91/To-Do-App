package com.example.to_do_app.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.to_do_app.R
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.viewmodels.SharedViewModel
import com.example.to_do_app.viewmodels.ToDoViewModel

class UpdateFragment : Fragment(), MenuProvider {

    private val args: UpdateFragmentArgs by navArgs()
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var currentTitle: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var currentDescription: EditText
    private lateinit var selectedToDo: ToDoModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        selectedToDo = args.toDoModel

        setUpdateMenu()

        setData(view, selectedToDo)

        return view
    }

    private fun setUpdateMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setData(view: View, todo: ToDoModel) {
        currentTitle = view.findViewById(R.id.current_todoTitle)
        spinnerPriority = view.findViewById(R.id.current_priority)
        currentDescription = view.findViewById(R.id.current_description)

        todo.apply {
            currentTitle.setText(title)
            spinnerPriority.setSelection(priority.ordinal)
            currentDescription.setText(description)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_update_todo -> {
                updateItem()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            R.id.menu_delete -> confirmDeleteItem()
        }
        return false
    }

    private fun updateItem() {
        if (sharedViewModel.verifyInputs(currentTitle, currentDescription)) {
            val toDo = ToDoModel(
                selectedToDo.id,
                sharedViewModel getValue currentTitle,
                sharedViewModel.parsePriority(spinnerPriority.selectedItem.toString()),
                sharedViewModel getValue currentDescription
            )
            toDoViewModel.updateToDo(toDo)
        }
    }

    private fun confirmDeleteItem() {
        val alertDialog = AlertDialog
            .Builder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                toDoViewModel.deleteToDO(selectedToDo)
                Toast.makeText(requireContext(), "Successfully removed", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            .setNegativeButton("No") { _, _ -> }
        alertDialog.setTitle("Delete ${selectedToDo.title}")
        alertDialog.setMessage("Are you sure to remove ${selectedToDo.title}")
        alertDialog.create().show()
    }
}