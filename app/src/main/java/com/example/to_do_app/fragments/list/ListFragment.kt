package com.example.to_do_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_app.R
import com.example.to_do_app.adapter.TodoAdapter
import com.example.to_do_app.data.models.ToDoModel
import com.example.to_do_app.databinding.FragmentListBinding
import com.example.to_do_app.viewmodels.SharedViewModel
import com.example.to_do_app.viewmodels.ToDoViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator

class ListFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {

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
    }

    private fun configRecyclerView() {

        val recyclerView = binding.recyclerView

        recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext().applicationContext)
        }
        recyclerView.itemAnimator = SlideInDownAnimator().apply {
            addDuration = 300
        }

        swipeToDeleteItem(recyclerView)
    }

    private fun swipeToDeleteItem(recyclerView: RecyclerView) {
        val swipeToDeleteCallback =
            object : SwipeToDelete() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    val itemToDelete = todoAdapter.toDoList[position]
                    toDoViewModel.deleteToDO(itemToDelete)

                    todoAdapter.notifyItemRemoved(position)
                    restoreDeletedItem(
                        viewHolder.itemView,
                        itemToDelete,
                        position
                    )
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedItem(view: View, toDo: ToDoModel, position: Int) {
        Snackbar.make(
            view, "1 Item Deleted", Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            toDoViewModel.insertData(toDo)
            todoAdapter.notifyItemChanged(position)
        }.show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_menu, menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThoughDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        println(newText)
        if (newText != null) {
            searchThoughDataBase(newText)
        }
        return true
    }

    private fun searchThoughDataBase(query: String) {
        val searchQuery = "%$query%"
        toDoViewModel.searchSearchToDo(searchQuery).observe(this) {
            todoAdapter.toDoList = it
        }

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_delete_all -> confirmDeleteAll()
            R.id.sortByHigh -> toDoViewModel.sortByHighPriority.observe(this) {
                todoAdapter.toDoList = it
            }
            R.id.sortByLow -> toDoViewModel.sortByLowPriority.observe(this) {
                todoAdapter.toDoList = it
            }
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
        } else {
            Toast.makeText(requireContext(), "You haven't set any todo", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}