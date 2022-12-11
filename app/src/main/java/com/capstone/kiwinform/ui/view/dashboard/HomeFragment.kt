package com.capstone.kiwinform.ui.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.FragmentHomeBinding
import com.capstone.kiwinform.ui.view.ListPlanAdapter
import com.capstone.kiwinform.ui.view.Plan
import com.capstone.kiwinform.ui.view.PlanViewModel
import com.capstone.kiwinform.ui.view.reminder.ReminderActivity

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?= null
    private val binding get() = _binding!!
    private lateinit var planViewModel: PlanViewModel

    //for animation
    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    private val repeatPeriod: Long = 3500

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

        runnable = Runnable {
            binding.pet.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake_anim))

            handler.postDelayed(runnable, repeatPeriod)
        }
        handler.postDelayed(runnable, 0)

        val listPlanAdapter = ListPlanAdapter()
        binding.rvTodaysActivities.setHasFixedSize(true)
        binding.rvTodaysActivities.layoutManager = LinearLayoutManager(activity)
        binding.rvTodaysActivities.adapter = listPlanAdapter

        planViewModel = (activity as MainActivity).planViewModel
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

        planViewModel.isAddedAnim.observe(viewLifecycleOwner, {
            if (it == true) {
                handler.removeCallbacks(runnable)
                binding.pet.startAnimation(AnimationUtils.loadAnimation(context, R.anim.bouncing_anim))
                handler.postDelayed(runnable, 4500)
            }
        })

        planViewModel.isDeletedAnim.observe(viewLifecycleOwner, {
            if(it == true) {
                handler.removeCallbacks(runnable)
                binding.pet.startAnimation(AnimationUtils.loadAnimation(context, R.anim.is_deleted_anim))
                handler.postDelayed(runnable, 3500)
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