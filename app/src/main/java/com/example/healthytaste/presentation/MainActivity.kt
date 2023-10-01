package com.example.healthytaste.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.healthytaste.R
import com.example.healthytaste.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationController()

    }

    private fun navigationController() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.frag_cont_view_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_first_dish,
                R.id.navigation_second_dish,
                R.id.navigation_dessert,
                R.id.navigation_kcal_calculator
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}