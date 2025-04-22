package com.fittracker.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fittracker.app.data.models.ExerciseSet
import java.util.*

/**
 * DAO для подходов упражнений
 */
@Dao
interface ExerciseSetDao {
    @Query("SELECT * FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId ORDER BY orderNumber ASC")
    fun getSetsForExercise(workoutExerciseId: Long): LiveData<List<ExerciseSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseSet: ExerciseSet): Long

    @Update
    suspend fun update(exerciseSet: ExerciseSet)

    @Delete
    suspend fun delete(exerciseSet: ExerciseSet)

    @Query("UPDATE exercise_sets SET completed = :completed, performedAt = :date WHERE id = :setId")
    suspend fun updateCompletionStatus(setId: Long, completed: Boolean, date: Date?)

    @Query("DELETE FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun deleteAllSetsForExercise(workoutExerciseId: Long)

    @Query("SELECT * FROM exercise_sets WHERE performedAt BETWEEN :startDate AND :endDate ORDER BY performedAt DESC")
    fun getSetsInDateRange(startDate: Date, endDate: Date): LiveData<List<ExerciseSet>>

    @Query("SELECT COUNT(*) FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun getSetCount(workoutExerciseId: Long): Int
} 