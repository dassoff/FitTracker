package com.fittracker.app.data.repositories

import kotlinx.coroutines.flow.Flow
import com.fittracker.app.data.database.ExerciseSetDao
import com.fittracker.app.data.models.ExerciseSet
import java.util.*

/**
 * Репозиторий для работы с подходами упражнений
 */
class ExerciseSetRepository(private val exerciseSetDao: ExerciseSetDao) {
    fun getSetsForWorkoutExercise(workoutExerciseId: Long): Flow<List<ExerciseSet>> {
        return exerciseSetDao.getSetsForWorkoutExercise(workoutExerciseId)
    }

    suspend fun insertAll(exerciseSets: List<ExerciseSet>) {
        exerciseSetDao.insertAll(exerciseSets)
    }

    suspend fun update(exerciseSet: ExerciseSet) {
        exerciseSetDao.update(exerciseSet)
    }

    suspend fun delete(exerciseSet: ExerciseSet) {
        exerciseSetDao.delete(exerciseSet)
        exerciseSetDao.reorderAfterDelete(exerciseSet.workoutExerciseId, exerciseSet.setNumber)
    }
    
    suspend fun deleteForWorkoutExercise(exerciseSet: ExerciseSet) {
        exerciseSetDao.delete(exerciseSet)
        exerciseSetDao.reorderAfterDeleteInWorkout(exerciseSet.workoutExerciseId, exerciseSet.setNumber)
    }

    suspend fun updateCompletionStatus(setId: Long, completed: Boolean, date: Date? = if (completed) Date() else null) {
        exerciseSetDao.updateCompletionStatus(setId, completed, date)
    }

    suspend fun deleteAllSetsForExercise(exerciseId: Long) {
        exerciseSetDao.deleteAllSetsForExercise(exerciseId)
    }
    
    suspend fun deleteAllSetsForWorkoutExercise(workoutExerciseId: Long) {
        exerciseSetDao.deleteAllSetsForWorkoutExercise(workoutExerciseId)
    }

    fun getExerciseSetsByDateRange(startDate: Date, endDate: Date): Flow<List<ExerciseSet>> {
        return exerciseSetDao.getExerciseSetsByDateRange(startDate, endDate)
    }

    fun getSetCount(exerciseId: Long): Flow<Int> {
        return exerciseSetDao.getSetCount(exerciseId)
    }
    
    suspend fun getWorkoutExerciseSetCount(workoutExerciseId: Long): Int {
        return exerciseSetDao.getWorkoutExerciseSetCount(workoutExerciseId)
    }

    suspend fun reorderAfterDelete(exerciseId: Long, position: Int) {
        exerciseSetDao.reorderAfterDelete(exerciseId, position)
    }
} 