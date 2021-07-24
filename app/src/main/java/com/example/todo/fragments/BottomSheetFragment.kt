package com.example.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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

//        binding.titleInput.setOnFocusChangeListener { v, hasFocus ->
//            if(!hasFocus){
//                if(binding.title.editText?.text.toString().isNullOrEmpty()) {
//                    binding.title.isErrorEnabled=true
//                    binding.title.error="Required Field"
//                }
//                binding.addInput.isEnabled = !(binding.title.isErrorEnabled || binding.description.isErrorEnabled)
//            }
//
//        }
//         use live data instead!!

//        binding.titleInput.setOnKeyListener(){
//                v: View?, keyCode: Int, event: KeyEvent? ->
//                event?.action?.equals(1)?.let{
//                    if((v as TextInputEditText).text.toString().isNullOrEmpty()){
//                        binding.title.isErrorEnabled=true
//                        binding.title.error="Required Field"
//                    }else binding.title.isErrorEnabled=false
//                    binding.addInput.isEnabled = !(binding.title.isErrorEnabled || binding.description.isErrorEnabled)
//                }
//
//                true
//        }
//        binding.descriptionInput.setOnKeyListener{
//                v: View?, keyCode: Int, event: KeyEvent? ->
//                event?.action?.equals(1)?.let{
//                    if((v as TextInputEditText).text.toString().isNullOrEmpty()){
//                        binding.description.isErrorEnabled=true
//                        binding.description.error="Required Field"
//                    }else binding.description.isErrorEnabled=false
//
//                    binding.addInput.isEnabled = !(binding.title.isErrorEnabled || binding.description.isErrorEnabled)
//                }
//
//            true
//        }

//        binding.descriptionInput.setOnFocusChangeListener { v, hasFocus ->
//            if(!hasFocus) {
//                if(binding.description.editText?.text.toString().isNullOrEmpty()) {
//                    binding.description.isErrorEnabled=true
//                    binding.description.error="Required Field"
//                    binding.addInput.isEnabled=false
//                }
//                binding.addInput.isEnabled = !(binding.title.isErrorEnabled || binding.description.isErrorEnabled)
//            }
//
//        }


        binding.addInput.setOnClickListener {
            val title = binding.title.editText?.text.toString()
            val description = binding.description.editText?.text.toString()

            if (title.isNullOrEmpty() || description.isNullOrEmpty()) {
                Toast.makeText(context, "Both inputs are requried", Toast.LENGTH_SHORT).show()
            } else {
                val direction =
                    BottomSheetFragmentDirections.actionBottomSheetFragmentToAvailableTasks(
                        title = title,
                        description = description
                    )
                findNavController().navigate(direction)
            }
        }
        return binding.root
    }


}



