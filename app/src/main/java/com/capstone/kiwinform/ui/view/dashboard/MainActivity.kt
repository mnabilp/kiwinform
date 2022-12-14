package com.capstone.kiwinform.ui.view.dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.ActivityMainBinding
import com.capstone.kiwinform.model.Plan
import com.capstone.kiwinform.ui.viewmodel.PlanViewModel
import com.capstone.kiwinform.ui.view.reminder.ReminderActivity
import com.capstone.kiwinform.utils.AlarmReceiver
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    lateinit var planViewModel: PlanViewModel
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_home,
            R.string.title_activities
        )

        private val TAB_ICONS = intArrayOf(
            R.drawable.ic_home,
            R.drawable.ic_activities
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        alarmReceiver = AlarmReceiver()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding?.viewPager?.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            tab.setIcon(TAB_ICONS[position])
            tab.setCustomView(R.layout.tab_layout_custom_view)
        }.attach()

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)

        binding!!.mainFab.setOnClickListener{
            ReminderActivity.launchAddPlanPage(this@MainActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ReminderActivity.REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Plan>(ReminderActivity.INTENT_PLAN)?.let { plan ->
                planViewModel.insertNotes(plan)
                planViewModel.setIsAddedAnim(true)

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    plan.date.toString(),
                    plan.time.toString(),
                    plan.title)
            }
        }

        else if (requestCode == ReminderActivity.REQUEST_CODE_ADD && resultCode == ReminderActivity.RESULT_IS_DELETED) {
            data?.getIntExtra(ReminderActivity.INTENT_PLAN_ID, 0)?.let { planId ->
                planViewModel.delete(planId)
                planViewModel.setIsDeletedAnim(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}