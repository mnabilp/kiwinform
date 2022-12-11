package com.capstone.kiwinform.model.database

import android.content.Context
import androidx.room.*
import com.capstone.kiwinform.utils.DateTimeConverter
import com.capstone.kiwinform.model.Plan

@Database(
    entities = [Plan::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)

abstract class PlanDatabase : RoomDatabase() {
    abstract fun planDao(): PlanDao

    companion object {
        @Volatile
        private var INSTANCE: PlanDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PlanDatabase {
            if (INSTANCE == null) {
                synchronized(PlanDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PlanDatabase::class.java, "plan_database")
                        .build()
                }
            }
            return INSTANCE as PlanDatabase
        }
    }
}