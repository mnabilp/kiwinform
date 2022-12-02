package com.capstone.kiwinform.ui.view

import androidx.lifecycle.LiveData

class PlanRepository(private val notesDao: PlanDao) {
    val getListPlan: LiveData<List<Plan>> = notesDao.getListPlan()

    suspend fun insertNotes(plan: Plan){
        notesDao.insert(plan)
    }

    suspend fun updateNotes(plan: Plan){
        notesDao.update(plan)
    }

    suspend fun deleteNotes(id: Int){
        notesDao.deletePlan(id)
    }
}