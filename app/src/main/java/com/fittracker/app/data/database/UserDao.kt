package com.fittracker.app.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fittracker.app.data.models.User

/**
 * DAO для пользователей
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User): Int

    @Delete
    suspend fun delete(user: User): Int

    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): LiveData<User?>
} 