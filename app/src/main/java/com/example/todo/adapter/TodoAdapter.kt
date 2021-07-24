package com.example.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.database.Task.Task
import com.example.todo.databinding.ListItemBinding

class TodoAdapter(private val alert: (task: Task) -> Unit) :
    ListAdapter<Task, TodoAdapter.TodoViewHolder>(DiffCallback) {
    class TodoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            var title: String = task.title
            if (task.title.length > 26) title = task.title.slice(0..23) + "..."
            binding.listItemTv.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val viewHolder =
            TodoViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
        viewHolder.itemView.setOnClickListener {
            var task = getItem(viewHolder.adapterPosition)
            alert(task)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return (oldItem.title == newItem.title && oldItem.description == newItem.description)
            }
        }
    }
}