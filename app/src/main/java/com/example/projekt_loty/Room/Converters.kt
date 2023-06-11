package com.example.projekt_loty.Room

import androidx.room.TypeConverter
import java.util.Date

// klasa niezbędna do obsługi kolumny z datą w bazie danych. Room nie wie, jak obchodzić się ze zmiennymi tego typu.

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}