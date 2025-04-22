package com.fittracker.app.data.repositories

import androidx.lifecycle.LiveData
import com.fittracker.app.data.database.ExerciseDao
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.MuscleGroup

/**
 * Репозиторий для работы с упражнениями
 */
class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val allExercises: LiveData<List<Exercise>> = exerciseDao.getAllExercises()
    val customExercises: LiveData<List<Exercise>> = exerciseDao.getCustomExercises()

    fun getExercisesByMuscleGroup(muscleGroup: MuscleGroup): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByMuscleGroup(muscleGroup)
    }

    fun getExerciseById(id: Long): LiveData<Exercise> {
        return exerciseDao.getExerciseById(id)
    }

    suspend fun insert(exercise: Exercise): Long {
        return exerciseDao.insert(exercise)
    }

    suspend fun update(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    suspend fun delete(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }

    fun searchExercises(query: String): LiveData<List<Exercise>> {
        return exerciseDao.searchExercises(query)
    }
} 