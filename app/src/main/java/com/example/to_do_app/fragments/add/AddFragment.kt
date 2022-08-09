package com.example.to_do_app.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.to_do_app.R
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.databinding.FragmentAddBinding
import com.example.to_do_app.viewmodels.SharedViewModel
import com.example.to_do_app.viewmodels.ToDoViewModel
import com.google.android.material.textfield.TextInputLayout

class AddFragment : Fragment(), MenuProvider {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleInput: TextInputLayout
    private lateinit var priorityInput: TextInputLayout
    private lateinit var descriptionInput: TextInputLayout

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val todoViewModel: ToDoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        configMenu()

        configPriorityList()

        setUpViews()

        return binding.root
    }

    private fun configMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configPriorityList() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, sharedViewModel.items)

        binding.autoCompleteText.apply {
            setAdapter(adapter)
        }

    }

    private fun setUpViews() {
        titleInput = binding.todoTitle
        priorityInput = binding.priorityMenu
        descriptionInput = binding.description
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.submit -> {
                if (sharedViewModel.verifyInputs(
                        titleInput.editText!!,
                        priorityInput.editText!!,
                        descriptionInput.editText!!
                    )
                ) {
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
            sharedViewModel getValue titleInput.editText!!,
            sharedViewModel.parsePriority(sharedViewModel getValue priorityInput.editText!!),
            sharedViewModel getValue descriptionInput.editText!!
        )
        todoViewModel.insertData(todo)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
