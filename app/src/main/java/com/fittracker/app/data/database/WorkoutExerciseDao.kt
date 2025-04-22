package com.fittracker.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.WorkoutExercise
import kotlinx.coroutines.flow.Flow

/**
 * DAO для упражнений в тренировке
 */
@Dao
interface WorkoutExerciseDao {
    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId ORDER BY orderNumber ASC")
    fun getExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutExercise: WorkoutExercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(workoutExercises: List<WorkoutExercise>): List<Long>

    @Update
    suspend fun update(workoutExercise: WorkoutExercise): Int

    @Delete
    suspend fun delete(workoutExercise: WorkoutExercise): Int

    @Query("SELECT * FROM workout_exercises WHERE id = :id")
    suspend fun getExerciseById(id: Long): WorkoutExercise

    @Query("DELETE FROM workout_exercises WHERE workoutId = :workoutId")
    suspend fun deleteAllExercisesForWorkout(workoutId: Long): Int

    @Query("UPDATE workout_exercises SET orderNumber = orderNumber - 1 WHERE workoutId = :workoutId AND orderNumber > :deletedPosition")
    suspend fun reorderAfterDelete(workoutId: Long, deletedPosition: Int): Int

    @Transaction
    @Query("SELECT e.* FROM exercises e INNER JOIN workout_exercises we ON e.id = we.exerciseId WHERE we.workoutId = :workoutId ORDER BY we.orderNumber ASC")
    fun getExerciseDetailsByWorkoutId(workoutId: Long): LiveData<List<Exercise>>
} 