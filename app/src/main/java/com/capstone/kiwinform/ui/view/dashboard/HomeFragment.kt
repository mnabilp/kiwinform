package com.capstone.kiwinform.ui.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.kiwinform.databinding.FragmentHomeBinding
import com.capstone.kiwinform.adapter.ListPlanAdapter
import com.capstone.kiwinform.model.Plan
import com.capstone.kiwinform.ui.viewmodel.PlanViewModel
import com.capstone.kiwinform.ui.view.reminder.ReminderActivity

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private lateinit var planViewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listPlanAdapter = ListPlanAdapter()
        binding.rvTodaysActivities.setHasFixedSize(true)
        binding.rvTodaysActivities.layoutManager = LinearLayoutManager(activity)
        binding.rvTodaysActivities.adapter = listPlanAdapter

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)
        planViewModel.todaysPlan.observe(viewLifecycleOwner, {
            if (it != null) {
                listPlanAdapter.setList(it)
            }
        })

        listPlanAdapter.setOnItemClickCallback(object : ListPlanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Plan) {
                showSelectedPlan(data)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSelectedPlan(plan: Plan) {
        val moveIntent = Intent(context, ReminderActivity::class.java)
        moveIntent.putExtra(ReminderActivity.EXTRA_PLAN, plan)
        activity?.startActivityForResult(moveIntent, ReminderActivity.REQUEST_CODE_ADD)
    }
}