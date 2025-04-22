package com.fittracker.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Модель пользователя
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String, // использовать UUID или ID от Firebase Auth
    val name: String,
    val email: String,
    val height: Float? = null, // рост в см
    val weight: Float? = null, // вес в кг
    val goal: Goal? = null,
    val createdAt: Date = Date(),
    val profileImageUrl: String? = null
)

/**
 * Цели тренировок
 */
enum class Goal {
    GAIN_MUSCLE,    // набор мышечной массы
    LOSE_WEIGHT,    // снижение веса
    MAINTAIN,       // поддержание формы
    STRENGTH,       // увеличение силы
    ENDURANCE       // выносливость
} 