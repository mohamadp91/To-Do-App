package com.example.to_do_app.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.R
import com.example.to_do_app.adapter.TodoAdapter
import com.example.to_do_app.viewmodels.ToDoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment(), MenuProvider {

    private val todoAdapter: TodoAdapter by lazy { TodoAdapter() }
    private val toDoViewModel: ToDoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        configNavigateListeners(view)

        configMenu()

        configToDoObservers()

        configRecyclerView(view)

        return view
    }


    private fun configNavigateListeners(view: View) {
        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                it.findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }

        view.findViewById<ConstraintLayout>(R.id.list_layout).setOnClickListener {
            it.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

    }

    private fun configMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configToDoObservers() {
        toDoViewModel.getAllData.observe(viewLifecycleOwner) {
            todoAdapter.toDoList = it
        }
    }

    private fun configRecyclerView(view: View) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

}