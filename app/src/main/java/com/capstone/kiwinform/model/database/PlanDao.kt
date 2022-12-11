package com.capstone.kiwinform.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.capstone.kiwinform.model.Plan
import java.time.LocalDate

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plan)

    @Update
    suspend fun update(plan: Plan)

    @Query("DELETE FROM plan_table WHERE id =:id")
    suspend fun deletePlan(id: Int)

    @Query("SELECT * FROM plan_table ORDER BY date ASC, time ASC")
    fun getListPlan(): LiveData<List<Plan>>

    @Query("SELECT * FROM plan_table WHERE date =:dateToday ORDER BY time ASC")
    fun getTodaysPlan(dateToday: LocalDate): LiveData<List<Plan>>
}