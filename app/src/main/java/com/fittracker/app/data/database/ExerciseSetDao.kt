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
    @Query("SELECT * FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId ORDER BY setNumber ASC")
    fun getSetsForWorkoutExercise(workoutExerciseId: Long): LiveData<List<ExerciseSet>>
    
    @Query("SELECT * FROM exercise_sets WHERE id = :setId")
    fun getSetById(setId: Long): LiveData<ExerciseSet>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exerciseSets: List<ExerciseSet>)

    @Update
    suspend fun update(exerciseSet: ExerciseSet)

    @Delete
    suspend fun delete(exerciseSet: ExerciseSet)

    @Query("UPDATE exercise_sets SET completed = :completed, performedAt = :date WHERE id = :setId")
    suspend fun updateCompletionStatus(setId: Long, completed: Boolean, date: Date?)

    @Query("DELETE FROM exercise_sets WHERE workoutExerciseId = :exerciseId")
    suspend fun deleteAllSetsForExercise(exerciseId: Long)
    
    @Query("DELETE FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun deleteAllSetsForWorkoutExercise(workoutExerciseId: Long)

    @Query("SELECT * FROM exercise_sets WHERE performedAt BETWEEN :startDate AND :endDate ORDER BY performedAt DESC")
    fun getExerciseSetsByDateRange(startDate: Date, endDate: Date): LiveData<List<ExerciseSet>>

    @Query("SELECT COUNT(*) FROM exercise_sets WHERE workoutExerciseId = :exerciseId")
    fun getSetCount(exerciseId: Long): LiveData<Int>
    
    @Query("SELECT COUNT(*) FROM exercise_sets WHERE workoutExerciseId = :workoutExerciseId")
    suspend fun getWorkoutExerciseSetCount(workoutExerciseId: Long): Int
    
    @Query("UPDATE exercise_sets SET setNumber = setNumber - 1 WHERE workoutExerciseId = :exerciseId AND setNumber > :position")
    suspend fun reorderAfterDelete(exerciseId: Long, position: Int)
    
    @Query("UPDATE exercise_sets SET setNumber = setNumber - 1 WHERE workoutExerciseId = :workoutExerciseId AND setNumber > :position")
    suspend fun reorderAfterDeleteInWorkout(workoutExerciseId: Long, position: Int)
} 