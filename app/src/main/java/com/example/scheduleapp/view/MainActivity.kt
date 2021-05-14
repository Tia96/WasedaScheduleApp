package com.example.scheduleapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scheduleapp.R
import com.example.scheduleapp.databinding.ActivityMainBinding
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment)?.findNavController() ?: throw NullPointerException()
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_schedule_table, R.id.navigation_webview), binding.root)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager.findFragmentById(R.id.navigation_host_fragment)?.findNavController() ?: throw NullPointerException()
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
