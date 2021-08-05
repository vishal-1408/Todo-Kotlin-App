package com.example.todo.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.coroutineScope
import com.example.todo.database.Task.Status
import com.example.todo.database.Task.Task
import com.example.todo.models.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageDialog(
    val status: Status,
    var task: Task,
    private val viewModel: TodoViewModel,
    var positiveButton: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(task.title)
            .setMessage(task.description)
            .setNegativeButton("Delete") { dialog, which ->

                if (task.status == Status.AVAILABLE) viewModel.clearAlarm(task, requireContext())
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    viewModel.deleteTask(task)

                }

                dialog.cancel()

            }.setPositiveButton(positiveButton) { dialog, which ->
                if (task.status == Status.AVAILABLE) viewModel.clearAlarm(task, requireContext())
                task.status = status
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    viewModel.updateData(task)

                }
                dialog.cancel()
            }
            .create()
    }

    companion object {
        val TAG = "DialogToShowTheTaskAndOptions"
    }
}