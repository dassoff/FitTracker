package com.fittracker.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Модель упражнения
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val muscleGroup: MuscleGroup,
    val imageUrl: String? = null,
    val isCustom: Boolean = false
) : Serializable

/**
 * Группы мышц
 */
enum class MuscleGroup {
    CHEST,
    BACK,
    LEGS,
    SHOULDERS,
    ARMS,
    ABS,
    FULL_BODY,
    CARDIO,
    OTHER
} 