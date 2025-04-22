package com.fittracker.app.data.repositories

import kotlinx.coroutines.flow.Flow
import com.fittracker.app.data.database.WorkoutDao
import com.fittracker.app.data.models.Workout
import java.util.Date

/**
 * Репозиторий для работы с тренировками
 */
class WorkoutRepository(private val workoutDao: WorkoutDao) {
    fun getAllWorkouts(): Flow<List<Workout>> {
        return workoutDao.getAllWorkouts()
    }

    fun getTemplateWorkouts(): Flow<List<Workout>> {
        return workoutDao.getTemplateWorkouts()
    }

    fun getCompletedWorkouts(): Flow<List<Workout>> {
        return workoutDao.getCompletedWorkouts()
    }

    fun searchWorkoutsByName(searchQuery: String): Flow<List<Workout>> {
        return workoutDao.searchWorkoutsByName(searchQuery)
    }

    fun getWorkoutById(workoutId: Long): Flow<Workout> {
        return workoutDao.getWorkoutById(workoutId)
    }

    suspend fun insert(workout: Workout): Long {
        return workoutDao.insert(workout)
    }

    suspend fun update(workout: Workout) {
        workoutDao.update(workout)
    }

    suspend fun delete(workout: Workout) {
        workoutDao.delete(workout)
    }

    suspend fun updateLastPerformedDate(workoutId: Long, date: Date) {
        workoutDao.updateLastPerformedDate(workoutId, date)
    }
} 