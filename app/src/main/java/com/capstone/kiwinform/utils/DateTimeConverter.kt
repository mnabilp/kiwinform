package com.capstone.kiwinform.utils

import android.os.Build
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateTimeConverter {
    @TypeConverter
    fun stringToLocalDate(value: String?): LocalDate? {
        return value?.let { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(value)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        }
    }

    @TypeConverter
    fun localDateToString(value: LocalDate?): String? {
        return value.toString()
    }

    @TypeConverter
    fun stringToLocalTime(value: String?): LocalTime? {
        return value?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalTime.parse(it)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }
    }

    @TypeConverter
    fun localTimetoString(value: LocalTime?): String? {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return value?.format(formatter)
    }
}