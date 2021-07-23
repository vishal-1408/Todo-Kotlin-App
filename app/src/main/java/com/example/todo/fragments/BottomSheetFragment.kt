package com.example.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment(){

    private lateinit var binding:FragmentBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater)
//        binding.cancelInput.setOnClickListener{
//            val direction = AvailableTasksDirections.actionAvailableTasksToBottomSheetFragment()
//            findNavController().navigate(direction)
//        }

        binding.addInput.setOnClickListener{
            val title = binding.title.editText?.text.toString()
            val description = binding.description.editText?.text.toString()
            val direction = BottomSheetFragmentDirections.actionBottomSheetFragmentToAvailableTasks(title=title,description = description)
                findNavController().navigate(direction)
        }
        return binding.root
    }


}