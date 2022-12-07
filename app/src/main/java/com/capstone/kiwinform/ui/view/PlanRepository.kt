package com.capstone.kiwinform.ui.view

import androidx.lifecycle.LiveData
import java.time.LocalDate

class PlanRepository(private val planDao: PlanDao) {
    val getListPlan: LiveData<List<Plan>> = planDao.getListPlan()

    val getTodaysPlan: LiveData<List<Plan>> = planDao.getTodaysPlan(LocalDate.now())

    suspend fun insertPlan(plan: Plan){
        planDao.insert(plan)
    }

    suspend fun updatePlan(plan: Plan){
        planDao.update(plan)
    }

    suspend fun deletePlan(id: Int){
        planDao.deletePlan(id)
    }
}