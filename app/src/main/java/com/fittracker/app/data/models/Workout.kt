package com.fittracker.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Модель тренировки
 */
@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val created: Date = Date(),
    val lastPerformed: Date? = null,
    val isTemplate: Boolean = false
) : Serializable 