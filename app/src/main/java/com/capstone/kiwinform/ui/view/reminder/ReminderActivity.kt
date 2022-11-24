package com.capstone.kiwinform.ui.view.reminder

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.kiwinform.databinding.ActivityReminderBinding
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReminderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.planDateEdit.setOnClickListener {
            updateDate()
        }
        
        binding.planTimeHourEdit.setOnClickListener { updateTime() }
        binding.planTimeMinuteEdit.setOnClickListener { updateTime() }

        binding.btnSavePlan.setOnClickListener { savePlan() }
    }

    private fun updateDate() {
        val myCalendar = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)

            var sFormat = "yyyy-MM-dd"
            val shortDateFormat = SimpleDateFormat(sFormat, Locale.US)

            sFormat = "EEEE, d MMM yyyy"
            val dateFormat = SimpleDateFormat(sFormat, Locale.US)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.planDateEdit.setText(dateFormat.format(myCalendar.time))
            }
        }

        DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateTime() {
        val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar.getInstance()
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)

        TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, { timePicker, selectedHour, selectedMinute ->
            binding.planTimeHourEdit.setText(checkDigit(selectedHour))
            binding.planTimeMinuteEdit.setText(checkDigit(selectedMinute))
        }, currentHour, currentMinute, true).show()
    }

    private fun checkDigit(number: Int): String {
        return if (number <= 9) "0$number"
        else number.toString()
    }

    private fun savePlan() {
        Toast.makeText(this, "TODO: Write code to save plan", Toast.LENGTH_SHORT).show()
    }
}