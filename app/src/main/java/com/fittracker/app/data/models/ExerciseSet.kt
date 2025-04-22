package com.fittracker.app.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

/**
 * Модель для подхода упражнения
 */
@Entity(
    tableName = "exercise_sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = ["id"],
            childColumns = ["workoutExerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutExerciseId"), Index("exerciseId"), Index("date")]
)
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val workoutExerciseId: Long,
    val exerciseId: Long,
    val weight: Float, // вес в кг
    val reps: Int, // количество повторений
    val completed: Boolean = false,
    val performedAt: Date? = null, // дата выполнения
    val setNumber: Int, // порядковый номер подхода
    val date: Date = Date() // дата создания подхода
) 