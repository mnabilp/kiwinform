package com.capstone.kiwinform.ui.view

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "plan_table")
@Parcelize
data class Plan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var date: String,
    var time: String
) :Parcelable
