package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todo.R
import com.example.todo.TodoApplication
import com.example.todo.databinding.FragmentStartedTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory

class StartedTasks : Fragment() {
    private val viewModel:TodoViewModel by activityViewModels {
        TodoViewModelFactory((activity?.application as TodoApplication).database.taskDao())
    }

    private lateinit var binding :FragmentStartedTasksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartedTasksBinding.inflate(inflater)
        return binding.root
    }

}