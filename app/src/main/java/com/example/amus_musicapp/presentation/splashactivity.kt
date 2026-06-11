package com.example.amus_musicapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.amus_musicapp.R
import com.example.amus_musicapp.ui.theme.SplashScreen

class splashactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen(
                onStartClick = {
                    startActivity(Intent(this@splashactivity, SongListActivity::class.java))
                    finish()
                }
            )
        }
    }
}