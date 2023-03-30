package com.alcorp.tracy.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.ActivityMainBinding
import com.alcorp.tracy.ui.auth.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.fragment))
//        bottomNav.setupWithNavController(navController)
        checkLogin()
    }

    private fun checkLogin() {
        val sharedPreferences = this.getSharedPreferences("account", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        if (token == ""){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}