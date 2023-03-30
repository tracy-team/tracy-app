package com.alcorp.tracy.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.alcorp.tracy.R
import com.alcorp.tracy.databinding.ActivitySplashBinding
import com.alcorp.tracy.ui.main.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Glide.with(this)
            .load(R.drawable.tracy)
            .apply(RequestOptions().override(200, 200))
            .into(binding.ivLogo)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}