package com.capstone.kiwinform.ui.view.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.kiwinform.databinding.FragmentActivitiesBinding
import com.capstone.kiwinform.ui.view.ListActivitiesPlanAdapter
import com.capstone.kiwinform.ui.view.Plan
import com.capstone.kiwinform.ui.view.PlanViewModel
import com.capstone.kiwinform.ui.view.reminder.ReminderActivity

class ActivitiesFragment : Fragment() {
    private var _binding : FragmentActivitiesBinding?= null
    private val binding get() = _binding!!
    private lateinit var planViewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listActivitiesPlanAdapter = ListActivitiesPlanAdapter()
        binding.rvActivitiesPlan.setHasFixedSize(true)
        binding.rvActivitiesPlan.layoutManager = LinearLayoutManager(activity)
        binding.rvActivitiesPlan.adapter = listActivitiesPlanAdapter

        planViewModel = ViewModelProvider(this).get(PlanViewModel::class.java)
        planViewModel.listAllPlan.observe(viewLifecycleOwner, {
            if (it != null) {
                listActivitiesPlanAdapter.setList(it)
            }
        })

        listActivitiesPlanAdapter.setOnItemClickCallback(object : ListActivitiesPlanAdapter.OnItemClickCallback {
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