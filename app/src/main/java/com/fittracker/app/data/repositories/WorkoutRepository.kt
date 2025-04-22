package com.fittracker.app.data.repositories

import androidx.lifecycle.LiveData
import com.fittracker.app.data.database.WorkoutDao
import com.fittracker.app.data.models.Workout
import java.util.*

/**
 * Репозиторий для работы с тренировками
 */
class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val allWorkouts: LiveData<List<Workout>> = workoutDao.getAllWorkouts()
    val workoutTemplates: LiveData<List<Workout>> = workoutDao.getWorkoutTemplates()
    val completedWorkouts: LiveData<List<Workout>> = workoutDao.getCompletedWorkouts()

    fun getWorkoutById(id: Long): LiveData<Workout> {
        return workoutDao.getWorkoutById(id)
    }

    suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    suspend fun update(workout: Workout): Int {
        return workoutDao.update(workout)
    }

    suspend fun delete(workout: Workout): Int {
        return workoutDao.delete(workout)
    }

    suspend fun updateLastPerformed(workoutId: Long, date: Date): Int {
        return workoutDao.updateLastPerformed(workoutId, date)
    }

    fun searchWorkouts(query: String): LiveData<List<Workout>> {
        return workoutDao.searchWorkouts(query)
    }
} 