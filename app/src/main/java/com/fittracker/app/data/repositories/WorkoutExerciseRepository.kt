package com.fittracker.app.data.repositories

import androidx.lifecycle.LiveData
import com.fittracker.app.data.database.WorkoutExerciseDao
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.WorkoutExercise
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutExerciseRepository @Inject constructor(
    private val workoutExerciseDao: WorkoutExerciseDao
) {
    fun getExercisesForWorkout(workoutId: Long): LiveData<List<WorkoutExercise>> {
        return workoutExerciseDao.getExercisesForWorkout(workoutId)
    }

    fun getExerciseDetailsByWorkoutId(workoutId: Long): LiveData<List<Exercise>> {
        return workoutExerciseDao.getExerciseDetailsByWorkoutId(workoutId)
    }

    suspend fun insert(workoutExercise: WorkoutExercise): Long {
        return workoutExerciseDao.insert(workoutExercise)
    }

    suspend fun update(workoutExercise: WorkoutExercise): Int {
        return workoutExerciseDao.update(workoutExercise)
    }

    suspend fun delete(workoutExercise: WorkoutExercise): Int {
        val result = workoutExerciseDao.delete(workoutExercise)
        // Перенумеруем оставшиеся упражнения
        workoutExerciseDao.reorderAfterDelete(workoutExercise.workoutId, workoutExercise.orderNumber)
        return result
    }

    suspend fun deleteAllExercisesForWorkout(workoutId: Long): Int {
        return workoutExerciseDao.deleteAllExercisesForWorkout(workoutId)
    }
} 