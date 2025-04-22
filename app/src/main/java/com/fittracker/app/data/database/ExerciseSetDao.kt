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
    @Query("SELECT * FROM exercise_sets WHERE exerciseId = :exerciseId ORDER BY setNumber ASC")
    fun getExerciseSets(exerciseId: Long): LiveData<List<ExerciseSet>>

    @Query("SELECT * FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId ORDER BY setNumber ASC")
    fun getSetsForWorkoutExercise(workoutExerciseId: Long): LiveData<List<ExerciseSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseSet: ExerciseSet): Long

    @Update
    suspend fun update(exerciseSet: ExerciseSet): Int

    @Delete
    suspend fun delete(exerciseSet: ExerciseSet): Int

    @Query("UPDATE exercise_sets SET completed = :completed, performedAt = :date WHERE id = :setId")
    suspend fun updateCompletionStatus(setId: Long, completed: Boolean, date: Date?)

    @Query("DELETE FROM exercise_sets WHERE exerciseId = :exerciseId")
    suspend fun deleteAllSetsForExercise(exerciseId: Long): Int

    @Query("DELETE FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun deleteAllSetsForWorkoutExercise(workoutExerciseId: Long): Int

    @Query("SELECT * FROM exercise_sets WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExerciseSetsByDateRange(startDate: Date, endDate: Date): LiveData<List<ExerciseSet>>

    @Query("UPDATE exercise_sets SET setNumber = setNumber - 1 WHERE exerciseId = :exerciseId AND setNumber > :position")
    suspend fun reorderAfterDelete(exerciseId: Long, position: Int): Int

    @Query("UPDATE exercise_sets SET setNumber = setNumber - 1 WHERE workoutExerciseId = :workoutExerciseId AND setNumber > :position")
    suspend fun reorderAfterDeleteInWorkout(workoutExerciseId: Long, position: Int): Int

    @Query("SELECT COUNT(*) FROM exercise_sets WHERE exerciseId = :exerciseId")
    suspend fun getSetCount(exerciseId: Long): Int

    @Query("SELECT COUNT(*) FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun getWorkoutExerciseSetCount(workoutExerciseId: Long): Int
} 