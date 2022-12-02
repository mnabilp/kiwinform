package com.capstone.kiwinform.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlanViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PlanRepository
    val listAllPlan: LiveData<List<Plan>>

    init {
        val notesDao = PlanDatabase.getDatabase(application).planDao()
        repository = PlanRepository(notesDao)
        listAllPlan = repository.getListPlan
    }

    fun insertNotes(plan: Plan) = viewModelScope.launch(Dispatchers.IO){
        repository.insertNotes(plan)
    }

    fun updateNotes(plan: Plan) = viewModelScope.launch(Dispatchers.IO){
        repository.updateNotes(plan)
    }

    fun delete(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNotes(id)
    }
}