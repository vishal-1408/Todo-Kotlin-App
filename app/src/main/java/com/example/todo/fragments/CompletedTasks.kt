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
import com.example.todo.databinding.FragmentCompletedTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CompletedTasks : Fragment() {
    private val viewModel:TodoViewModel by activityViewModels {
        TodoViewModelFactory((activity?.application as TodoApplication).database.taskDao())
    }
    private lateinit var binding:FragmentCompletedTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedTasksBinding.inflate(inflater)

        // NTG WILLHAPPEN FOR NOW WHEN WE CLICK ON THE ELEMENTS
        val adapter = TodoAdapter({})
        binding.completedTasksRv.adapter = adapter
        binding.completedTasksRv.layoutManager = GridLayoutManager(context, 1)
//        binding.completedTasksRv.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        lifecycle.coroutineScope.launch {
            viewModel.getByStatus(Status.COMPLETED).collect {
                adapter.submitList(it)
                if (it.size == 0) binding.noCompletedItems.visibility = View.VISIBLE
                else binding.noCompletedItems.visibility = View.GONE
            }
        }
        return binding.root
    }


}