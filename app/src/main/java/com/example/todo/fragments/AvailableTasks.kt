package com.example.todo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.TodoApplication
import com.example.todo.adapter.TodoAdapter
import com.example.todo.database.Task.Status
import com.example.todo.databinding.FragmentAvailableTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AvailableTasks : Fragment() {
    private val viewModel:TodoViewModel by activityViewModels{
        TodoViewModelFactory((activity?.application as TodoApplication).database.taskDao())
    }
    private lateinit var binding:FragmentAvailableTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("available", "about to enter")
        arguments?.let {
            val title = it.getString("title")
            val description = it.getString("description")
            if (!title.isNullOrEmpty() && !description.isNullOrEmpty()) {
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    viewModel.insertData(title.trim(), description.trim(), Status.AVAILABLE)
                }
            }
        }
        Log.i("available", "came outside!!")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAvailableTasksBinding.inflate(inflater)
        binding.floatingActionButton.setOnClickListener {
            val directions = AvailableTasksDirections.actionAvailableTasksToBottomSheetFragment()
            findNavController().navigate(directions)
        }
        val adapter = TodoAdapter {
            MessageDialog(Status.STARTED, it, viewModel, "Start").show(
                childFragmentManager,
                MessageDialog.TAG
            )
        }
        binding.availableRv.adapter = adapter
        binding.availableRv.layoutManager = LinearLayoutManager(context)
        binding.availableRv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        lifecycle.coroutineScope.launch {
            viewModel.getByStatus(Status.AVAILABLE).collect {
                adapter.submitList(it)
                if (it.size == 0) binding.noAvailItems.visibility = View.VISIBLE
                else binding.noAvailItems.visibility = View.GONE
            }
        }
        return binding.root
    }

}