package com.capstone.kiwinform.model.repository

import androidx.lifecycle.LiveData
import com.capstone.kiwinform.model.Plan
import com.capstone.kiwinform.model.database.PlanDao
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