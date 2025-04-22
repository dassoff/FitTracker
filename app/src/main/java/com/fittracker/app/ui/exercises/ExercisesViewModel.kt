package com.fittracker.app.ui.exercises

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fittracker.app.data.models.Exercise
import com.fittracker.app.data.models.MuscleGroup
import com.fittracker.app.data.repositories.ExerciseRepository
import kotlinx.coroutines.launch

/**
 * ViewModel для экрана упражнений
 */
class ExercisesViewModel(private val repository: ExerciseRepository) : ViewModel() {

    // Получаем все упражнения из репозитория
    val allExercises = repository.allExercises

    // Выбранная группа мышц
    private val _selectedMuscleGroup = MutableLiveData<MuscleGroup?>()
    val selectedMuscleGroup: LiveData<MuscleGroup?> = _selectedMuscleGroup

    // Отфильтрованные упражнения
    private val _filteredExercises = MutableLiveData<List<Exercise>>()
    val filteredExercises: LiveData<List<Exercise>> = _filteredExercises

    // Поисковый запрос
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    // Результат поиска
    private val _searchResults = MutableLiveData<List<Exercise>>()
    val searchResults: LiveData<List<Exercise>> = _searchResults

    // Выбранное упражнение для просмотра или редактирования
    private val _selectedExercise = MutableLiveData<Exercise?>()
    val selectedExercise: LiveData<Exercise?> = _selectedExercise

    /**
     * Установка фильтра по группе мышц
     */
    fun setMuscleGroupFilter(muscleGroup: MuscleGroup?) {
        _selectedMuscleGroup.value = muscleGroup
        updateFilteredExercises()
    }

    /**
     * Обновление отфильтрованных упражнений
     */
    private fun updateFilteredExercises() {
        val selected = _selectedMuscleGroup.value
        if (selected == null) {
            _filteredExercises.value = allExercises.value ?: emptyList()
        } else {
            viewModelScope.launch {
                val result = repository.getExercisesByMuscleGroup(selected).value ?: emptyList()
                _filteredExercises.postValue(result)
            }
        }
    }

    /**
     * Поиск упражнений
     */
    fun searchExercises(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            val results = repository.searchExercises(query).value ?: emptyList()
            _searchResults.postValue(results)
        }
    }

    /**
     * Выбор упражнения для просмотра или редактирования
     */
    fun selectExercise(exercise: Exercise?) {
        _selectedExercise.value = exercise
    }

    /**
     * Добавление нового упражнения
     */
    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.insert(exercise)
        }
    }

    /**
     * Обновление упражнения
     */
    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.update(exercise)
        }
    }

    /**
     * Удаление упражнения
     */
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.delete(exercise)
        }
    }

    /**
     * Factory для создания ExercisesViewModel с внедрением зависимостей
     */
    class Factory(private val repository: ExerciseRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExercisesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ExercisesViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
} 