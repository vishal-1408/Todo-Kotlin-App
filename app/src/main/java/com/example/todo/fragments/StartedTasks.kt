package com.example.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todo.TodoApplication
import com.example.todo.adapter.TodoAdapter
import com.example.todo.database.Task.Status
import com.example.todo.databinding.FragmentStartedTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        val adapter = TodoAdapter {
            MessageDialog(Status.COMPLETED, it, viewModel, "Done").show(
                childFragmentManager,
                MessageDialog.TAG
            )
        }

        binding.startedTasksRv.adapter = adapter
        binding.startedTasksRv.layoutManager = GridLayoutManager(context, 1)
//        binding.startedTasksRv.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        lifecycle.coroutineScope.launch {
            viewModel.getByStatus(Status.STARTED).collect {
                adapter.submitList(it)
                if (it.size == 0) binding.noStartedItems.visibility = View.VISIBLE
                else binding.noStartedItems.visibility = View.GONE
            }
        }
        return binding.root
    }

}