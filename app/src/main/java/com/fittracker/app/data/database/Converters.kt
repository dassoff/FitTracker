package com.fittracker.app.data.database

import androidx.room.TypeConverter
import com.fittracker.app.data.models.Goal
import com.fittracker.app.data.models.MuscleGroup
import java.util.*

/**
 * Конвертеры типов для Room
 */
class Converters {
    // Конвертеры для Date
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Конвертеры для MuscleGroup
    @TypeConverter
    fun fromMuscleGroup(muscleGroup: MuscleGroup): String {
        return muscleGroup.name
    }

    @TypeConverter
    fun toMuscleGroup(value: String): MuscleGroup {
        return enumValueOf(value)
    }

    // Конвертеры для Goal
    @TypeConverter
    fun fromGoal(goal: Goal?): String? {
        return goal?.name
    }

    @TypeConverter
    fun toGoal(value: String?): Goal? {
        return value?.let { enumValueOf<Goal>(it) }
    }
} 