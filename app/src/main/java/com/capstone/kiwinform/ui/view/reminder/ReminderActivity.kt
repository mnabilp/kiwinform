package com.capstone.kiwinform.ui.view.reminder

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.ActivityReminderBinding
import com.capstone.kiwinform.ui.view.Plan
import com.capstone.kiwinform.ui.view.PlanViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReminderActivity : AppCompatActivity() {
    companion object{
        const val REQUEST_CODE_ADD = 1000
        const val INTENT_PLAN = "intent_plan"

        const val EXTRA_PLAN = "extra_plan"

        fun launchAddPlanPage(activity: Activity){
            val intent = Intent(activity, ReminderActivity::class.java)
            activity.startActivityForResult(intent, REQUEST_CODE_ADD)
        }
    }

    private lateinit var binding: ActivityReminderBinding
    private lateinit var planViewModel: PlanViewModel

    private var planId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        if (intent.hasExtra(EXTRA_PLAN)) {
            val plan = intent.getParcelableExtra<Plan>(EXTRA_PLAN)
            planId = plan!!.id

            binding.apply {
                planTitleEditText.setText(plan.title)
                planDescEditText.setText(plan.description)
                planDateEdit.setText(plan.date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy")))
                planTimeHourEdit.setText(plan.time.toString().take(2))
                planTimeMinuteEdit.setText(plan.time.toString().takeLast(2))
            }
        }

        binding.planDateEdit.setOnClickListener {
            updateDate()
        }
        
        binding.planTimeHourEdit.setOnClickListener { updateTime() }
        binding.planTimeMinuteEdit.setOnClickListener { updateTime() }

        binding.btnSavePlan.setOnClickListener {
            if (intent.hasExtra(EXTRA_PLAN)) {
                updatePlan()
            } else { savePlan() }
        }
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

            val sFormat = "EEEE, d MMM yyyy"
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
        val data = Intent()
        val titlePlan = binding.planTitleEditText.text.toString()
        val descPlan = binding.planDescEditText.text.toString()

        val toBeConverted = binding.planDateEdit.text.toString()
        val format = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy")
        val date = LocalDate.parse(toBeConverted, format)

        val hour = binding.planTimeHourEdit.text.toString()
        val minute = binding.planTimeMinuteEdit.text.toString()
        val time = LocalTime.parse("$hour:$minute")

        val plan = Plan(title = titlePlan, description = descPlan, date = date, time = time)
        data.putExtra(INTENT_PLAN, plan)
        setResult(Activity.RESULT_OK, data)

        finish()
    }

    private fun updatePlan() {
        val data = Intent()
        val titlePlan = binding.planTitleEditText.text.toString()
        val descPlan = binding.planDescEditText.text.toString()

        val toBeConverted = binding.planDateEdit.text.toString()
        val format = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy")
        val date = LocalDate.parse(toBeConverted, format)

        val hour = binding.planTimeHourEdit.text.toString()
        val minute = binding.planTimeMinuteEdit.text.toString()
        val time = LocalTime.parse("$hour:$minute")

        val plan = Plan(id = planId, title = titlePlan, description = descPlan, date = date, time = time)

        data.putExtra(INTENT_PLAN, plan)
        setResult(Activity.RESULT_OK, data)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reminder_update_menu, menu)
        if (intent.hasExtra(EXTRA_PLAN)) {
            val item = menu!!.findItem(R.id.delete)
            item.isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                planViewModel.delete(planId)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}