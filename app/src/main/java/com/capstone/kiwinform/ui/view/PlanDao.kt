package com.capstone.kiwinform.ui.view

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plan)

    @Update
    suspend fun update(plan: Plan)

    @Query("DELETE FROM plan_table WHERE id =:id")
    suspend fun deletePlan(id: Int)

    @Query("SELECT * FROM plan_table ORDER BY id ASC")
    fun getListPlan(): LiveData<List<Plan>>
}