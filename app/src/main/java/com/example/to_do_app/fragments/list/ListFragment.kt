package com.example.to_do_app.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.to_do_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {
                view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }

        view.findViewById<ConstraintLayout>(R.id.list_layout).setOnClickListener {
            view.findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
        return view
    }
}