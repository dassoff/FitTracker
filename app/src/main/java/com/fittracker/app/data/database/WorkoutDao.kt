package com.fittracker.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fittracker.app.data.models.Workout
import java.util.*

/**
 * DAO для тренировок
 */
@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY name ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE isTemplate = 1 ORDER BY name ASC")
    fun getWorkoutTemplates(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE isTemplate = 0 ORDER BY dateCompleted DESC")
    fun getCompletedWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :id")
    fun getWorkoutById(id: Long): LiveData<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout): Long

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * FROM workouts WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchWorkouts(query: String): LiveData<List<Workout>>

    @Query("UPDATE workouts SET lastPerformedDate = :date WHERE id = :workoutId")
    suspend fun updateLastPerformedDate(workoutId: Long, date: Date)
} 