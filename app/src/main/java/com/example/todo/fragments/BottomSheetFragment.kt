package com.example.todo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private val SELECT_TIME = "SELECT_TIME"
    private var selectedDateInMillis: Long? = null
    private var selectedTimeHour: Int? = null
    private var selectedTimeMinute: Int? = null
    private var datePickerTag: String? = null
    private var timePickerTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            selectedDateInMillis = it.getLong(SELECT_TIME)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater)
        buildTimePicker()
        buildDatePicker()
        binding.addInput.setOnClickListener {
            val title = binding.title.editText?.text.toString()
            val description = binding.description.editText?.text.toString()
            if (title.isNullOrEmpty() || description.isNullOrEmpty() || selectedDateInMillis.toString()
                    .isNullOrEmpty()
                || selectedTimeHour.toString().isNullOrEmpty() || selectedTimeMinute.toString()
                    .isNullOrEmpty()
            ) {
                Toast.makeText(context, "All inputs are required", Toast.LENGTH_SHORT).show()
            } else {

                val calendar = combineDateAndTimePickerResults()
                Log.i("INSERT", SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.time))

                val direction =
                    BottomSheetFragmentDirections.actionBottomSheetFragmentToAvailableTasks(
                        title = title,
                        description = description,
                        notificationTime = calendar.timeInMillis
                    )
                findNavController().navigate(direction)
            }
        }


        return binding.root
    }

    private fun combineDateAndTimePickerResults(): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = selectedDateInMillis!!
        calendar.setTimeZone(TimeZone.getDefault())
        calendar[Calendar.HOUR_OF_DAY] = selectedTimeHour!!
        calendar[Calendar.MINUTE] = selectedTimeMinute!!
        //calendar.setTimeZone(TimeZone.getTimeZone("UTC"))
        return calendar
        Toast.makeText(
            context,
            SimpleDateFormat("dd MMMM yyyy HH:mm").format(calendar.time),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun buildTimePicker() {
        binding.timePickInput.isFocusable = false
        binding.timePickInput.isCursorVisible = false


        val timePicker = MaterialTimePicker.Builder()
            .setTitleText("Pick a time")
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(Calendar.getInstance()[Calendar.HOUR_OF_DAY])
            .setMinute(Calendar.getInstance()[Calendar.MINUTE])
            .build()
        timePicker.addOnPositiveButtonClickListener {
            val hour = timePicker.hour
            val minute = timePicker.minute
            val formatString = when {
                hour < 10 -> {
                    if (minute < 10) "0$hour:0$minute"
                    else "0$hour:$minute"
                }
                else -> {
                    if (minute < 10) "$hour:0$minute"
                    else "$hour:$minute"
                }
            }
            binding.timePickInput.setText(formatString.toCharArray(), 0, formatString.length)
            selectedTimeHour = hour
            selectedTimeMinute = minute
        }

        binding.timePickInput.setOnClickListener {
            timePicker.show(parentFragmentManager, "TIMEPICKER")
            timePickerTag = "TIMEPICKER"
        }
    }

    private fun buildDatePicker() {

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        // to make the input box un-editable, due to which we can show the datepicker
        binding.datePickInput.isFocusable = false
        binding.datePickInput.isCursorVisible = false
        // this is the startDate for constraints
        val startDateInMillis = selectedDateInMillis ?: calendar.timeInMillis

        calendar[Calendar.MONTH] = calendar[Calendar.MONTH].plus(1)

        // enddate for constraints
        val endDateInMillis = calendar.timeInMillis

        val calendarConstraints = CalendarConstraints.Builder()
            .setStart(startDateInMillis) //set Start constrains the start and it takes the timeInMillis
            .setEnd(endDateInMillis)
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select The Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(calendarConstraints)
            .build()

        // when clicked ok this is fired
        datePicker.addOnPositiveButtonClickListener {
            selectedDateInMillis = it
            Log.i("BOTTOM", it.toString())
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it
            val dateString = SimpleDateFormat("dd MMMM yyyy").format(calendar.time).toString()
            binding.datePickInput.setText(dateString.toCharArray(), 0, dateString.length)
        }


        binding.datePickInput.setOnClickListener {
            // is it required to set the timeInMillis to UTC timeinMillis using MaterialDatePicker.todayInUtcMilliSeconds()
            datePicker.show(parentFragmentManager, "DATEPICKER")
            datePickerTag = "DATEPICKER"

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(SELECT_TIME, selectedDateInMillis!!)
    }


}



