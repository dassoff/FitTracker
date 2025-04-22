package com.fittracker.app.data.repositories

import androidx.lifecycle.LiveData
import com.fittracker.app.data.database.ExerciseSetDao
import com.fittracker.app.data.models.ExerciseSet
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseSetRepository @Inject constructor(
    private val exerciseSetDao: ExerciseSetDao
) {
    fun getExerciseSets(exerciseId: Long): LiveData<List<ExerciseSet>> {
        return exerciseSetDao.getExerciseSets(exerciseId)
    }
    
    fun getSetsForWorkoutExercise(workoutExerciseId: Long): LiveData<List<ExerciseSet>> {
        return exerciseSetDao.getSetsForWorkoutExercise(workoutExerciseId)
    }

    suspend fun insert(exerciseSet: ExerciseSet): Long {
        return exerciseSetDao.insert(exerciseSet)
    }

    suspend fun update(exerciseSet: ExerciseSet): Int {
        return exerciseSetDao.update(exerciseSet)
    }

    suspend fun delete(exerciseSet: ExerciseSet): Int {
        val result = exerciseSetDao.delete(exerciseSet)
        exerciseSetDao.reorderAfterDelete(exerciseSet.exerciseId, exerciseSet.setNumber)
        return result
    }
    
    suspend fun deleteForWorkoutExercise(exerciseSet: ExerciseSet): Int {
        val result = exerciseSetDao.delete(exerciseSet)
        exerciseSetDao.reorderAfterDeleteInWorkout(exerciseSet.workoutExerciseId, exerciseSet.setNumber)
        return result
    }

    suspend fun updateCompletionStatus(setId: Long, completed: Boolean, date: Date? = if (completed) Date() else null): Int {
        return exerciseSetDao.updateCompletionStatus(setId, completed, date)
    }

    suspend fun deleteAllSetsForExercise(exerciseId: Long): Int {
        return exerciseSetDao.deleteAllSetsForExercise(exerciseId)
    }
    
    suspend fun deleteAllSetsForWorkoutExercise(workoutExerciseId: Long): Int {
        return exerciseSetDao.deleteAllSetsForWorkoutExercise(workoutExerciseId)
    }

    fun getExerciseSetsByDateRange(startDate: Date, endDate: Date): LiveData<List<ExerciseSet>> {
        return exerciseSetDao.getExerciseSetsByDateRange(startDate, endDate)
    }

    suspend fun getSetCount(exerciseId: Long): Int {
        return exerciseSetDao.getSetCount(exerciseId)
    }
    
    suspend fun getWorkoutExerciseSetCount(workoutExerciseId: Long): Int {
        return exerciseSetDao.getWorkoutExerciseSetCount(workoutExerciseId)
    }
} 