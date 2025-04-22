package com.fittracker.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fittracker.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Главная активность приложения
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Настройка нижней навигации
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        
        // Связываем контроллер навигации с нижним меню
        navView.setupWithNavController(navController)
    }
} 