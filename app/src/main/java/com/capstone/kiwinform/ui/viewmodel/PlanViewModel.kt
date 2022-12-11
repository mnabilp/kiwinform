package com.capstone.kiwinform.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.capstone.kiwinform.model.Plan
import com.capstone.kiwinform.model.database.PlanDatabase
import com.capstone.kiwinform.model.repository.PlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PlanRepository
    val listAllPlan: LiveData<List<Plan>>
    val todaysPlan: LiveData<List<Plan>>

    init {
        val planDao = PlanDatabase.getDatabase(application).planDao()
        repository = PlanRepository(planDao)

        listAllPlan = repository.getListPlan

        todaysPlan = repository.getTodaysPlan
    }

    fun insertNotes(plan: Plan) = viewModelScope.launch(Dispatchers.IO){
        repository.insertPlan(plan)
    }

    fun updateNotes(plan: Plan) = viewModelScope.launch(Dispatchers.IO){
        repository.updatePlan(plan)
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePlan(id)
    }
}