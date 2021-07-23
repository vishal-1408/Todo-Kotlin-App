package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.example.todo.R
import com.example.todo.TodoApplication
import com.example.todo.database.Task.Status
import com.example.todo.database.Task.Task
import com.example.todo.database.Task.TaskDao
import com.example.todo.databinding.FragmentAvailableTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AvailableTasks : Fragment() {
    private val viewModel:TodoViewModel by activityViewModels{
        TodoViewModelFactory((activity?.application as TodoApplication).database.taskDao())
    }
    private lateinit var binding:FragmentAvailableTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            val title = it.getString("title").toString()
            val description = it.getString("description").toString()
            viewModel.insertData(title,description,Status.AVAILABLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAvailableTasksBinding.inflate(inflater)
        binding.floatingActionButton.setOnClickListener{
            val directions = AvailableTasksDirections.actionAvailableTasksToBottomSheetFragment()
            findNavController().navigate(directions)
        }
        return binding.root
    }

}