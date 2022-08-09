package com.example.to_do_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_app.R
import com.example.to_do_app.adapter.TodoAdapter
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.databinding.FragmentListBinding
import com.example.to_do_app.viewmodels.SharedViewModel
import com.example.to_do_app.viewmodels.ToDoViewModel

class ListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }
    private val toDoViewModel: ToDoViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var toDoModels = emptyList<ToDoModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        configNavigateListeners()

        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        configMenu()

        configToDoObservers()

        configRecyclerView()

        return binding.root
    }


    private fun configNavigateListeners() {
        binding.listLayout.setOnClickListener {
            it.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

    }

    private fun configMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configToDoObservers() {

        toDoViewModel.getAllData.observe(viewLifecycleOwner) {
            toDoModels = it
            todoAdapter.toDoList = toDoModels
            sharedViewModel.checkIsDatabaseEmpty(toDoModels)
        }

        sharedViewModel.isDatabaseEmpty.observe(viewLifecycleOwner) {
            updateScreenByTodos()
        }
    }

    private fun updateScreenByTodos() {
//        if (toDoModels.isEmpty()) {
//            binding.noDataImage.visibility = View.VISIBLE
//            binding.noDataText.visibility = View.VISIBLE
//        } else {
//            binding.noDataImage.visibility = View.GONE
//            binding.noDataText.visibility = View.GONE
//        }
    }

    private fun configRecyclerView() {

        val recyclerView = binding.recyclerView

        recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_delete_all -> confirmDeleteAll()
        }
        return false
    }

    private fun confirmDeleteAll() {
        if (toDoModels.isNotEmpty()) {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setPositiveButton("Yes") { _, _ ->
                    toDoViewModel.deleteAllToDos()
                }
                .setNegativeButton("No", null)
            alertDialog.setTitle("Delete ${toDoModels.size} todos ?")
            alertDialog.setMessage("Are sure you want to remove all todos ?")
            alertDialog.create().show()
            updateScreenByTodos()
        } else {
            Toast.makeText(requireContext(), "You haven't set any todo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}