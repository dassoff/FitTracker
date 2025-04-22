package com.fittracker.app

import android.app.Application
import com.fittracker.app.data.database.AppDatabase
import com.fittracker.app.data.repositories.ExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Класс приложения
 */
class FitTrackerApp : Application() {
    // ApplicationScope для корутин, которые будут жить во время жизни приложения
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Ленивая инициализация базы данных
    val database by lazy { AppDatabase.getDatabase(this) }
    
    // Ленивая инициализация репозитория упражнений
    val exerciseRepository by lazy { ExerciseRepository(database.exerciseDao()) }

    override fun onCreate() {
        super.onCreate()
        // Инициализация базы данных предустановленными данными может быть здесь
    }
} 