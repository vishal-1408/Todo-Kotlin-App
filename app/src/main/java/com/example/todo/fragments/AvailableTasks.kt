package com.example.todo.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todo.R
import com.example.todo.TodoApplication
import com.example.todo.adapter.TodoAdapter
import com.example.todo.database.Task.Status
import com.example.todo.databinding.FragmentAvailableTasksBinding
import com.example.todo.models.TodoViewModel
import com.example.todo.models.TodoViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val CHANNEL_ID = 1
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
            val notificationTime = it.getLong("notificationTime")
            if (!title.isNullOrEmpty() && !description.isNullOrEmpty()) {
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    viewModel.insertData(
                        title.trim(),
                        description.trim(),
                        Status.AVAILABLE,
                        notificationTime,
                        requireContext()
                    )
                }
            }


        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = com.example.todo.databinding.FragmentAvailableTasksBinding.inflate(inflater)
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
        binding.availableRv.layoutManager = GridLayoutManager(context, 1)
//        binding.availableRv.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
        lifecycle.coroutineScope.launch {
            viewModel.getByStatus(Status.AVAILABLE).collect {
                adapter.submitList(it)
                if (it.size == 0) binding.noAvailItems.visibility = View.VISIBLE
                else binding.noAvailItems.visibility = View.GONE
            }
        }

        createChannel()

        return binding.root
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                requireContext().getString(R.string.channel_name),
                requireContext().getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.description =
                "Channel for notification that notify to start the tasks"

            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}