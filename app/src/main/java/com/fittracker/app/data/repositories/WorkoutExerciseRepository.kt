package com.fittracker.app.data.repositories

import kotlinx.coroutines.flow.Flow
import com.fittracker.app.data.database.WorkoutExerciseDao
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.WorkoutExercise

/**
 * Репозиторий для работы со связями тренировок и упражнений
 */
class WorkoutExerciseRepository(private val workoutExerciseDao: WorkoutExerciseDao) {
    fun getExercisesForWorkout(workoutId: Long): Flow<List<WorkoutExercise>> {
        return workoutExerciseDao.getExercisesForWorkout(workoutId)
    }

    fun getExerciseDetailsByWorkoutId(workoutId: Long): Flow<List<Exercise>> {
        return workoutExerciseDao.getExerciseDetailsByWorkoutId(workoutId)
    }

    suspend fun insert(workoutExercise: WorkoutExercise): Long {
        return workoutExerciseDao.insert(workoutExercise)
    }

    suspend fun update(workoutExercise: WorkoutExercise) {
        workoutExerciseDao.update(workoutExercise)
    }

    suspend fun delete(workoutExercise: WorkoutExercise) {
        workoutExerciseDao.delete(workoutExercise)
    }

    fun getExerciseById(id: Long): Flow<WorkoutExercise> {
        return workoutExerciseDao.getExerciseById(id)
    }

    suspend fun deleteAllExercisesForWorkout(workoutId: Long) {
        workoutExerciseDao.deleteAllExercisesForWorkout(workoutId)
    }

    suspend fun reorderAfterDelete(workoutId: Long, position: Int) {
        workoutExerciseDao.reorderAfterDelete(workoutId, position)
    }
} 