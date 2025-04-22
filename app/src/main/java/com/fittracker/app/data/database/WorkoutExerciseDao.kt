package com.fittracker.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.WorkoutExercise

@Dao
interface WorkoutExerciseDao {
    @Query("SELECT * FROM workout_exercises WHERE workoutId = :workoutId ORDER BY orderNumber ASC")
    fun getExercisesForWorkout(workoutId: Long): LiveData<List<WorkoutExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutExercise: WorkoutExercise): Long

    @Update
    suspend fun update(workoutExercise: WorkoutExercise)

    @Delete
    suspend fun delete(workoutExercise: WorkoutExercise)

    @Transaction
    @Query("SELECT e.* FROM exercises e INNER JOIN workout_exercises we ON e.id = we.exerciseId WHERE we.workoutId = :workoutId ORDER BY we.orderNumber ASC")
    fun getExerciseDetailsByWorkoutId(workoutId: Long): LiveData<List<Exercise>>

    @Query("DELETE FROM workout_exercises WHERE workoutId = :workoutId")
    suspend fun deleteAllExercisesForWorkout(workoutId: Long)

    @Query("UPDATE workout_exercises SET orderNumber = orderNumber - 1 WHERE workoutId = :workoutId AND orderNumber > :position")
    suspend fun reorderAfterDelete(workoutId: Long, position: Int)
} 