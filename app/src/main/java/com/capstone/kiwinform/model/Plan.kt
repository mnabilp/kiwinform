package com.capstone.kiwinform.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "plan_table")
@Parcelize
data class Plan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var date: LocalDate,
    var time: LocalTime
) :Parcelable
