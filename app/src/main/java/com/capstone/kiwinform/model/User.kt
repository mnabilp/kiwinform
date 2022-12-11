package com.capstone.kiwinform.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "plan_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var fullName: String,
    var experiences: Int = 0,
    val plans: List<Plan>
) :Parcelable
