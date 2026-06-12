package com.example.amus_musicapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.amus_musicapp.R
import com.example.amus_musicapp.data.Songs
import com.example.amus_musicapp.ui.theme.MuzikCalarEkran

class MuzikCalarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mySongList=intent.getParcelableArrayListExtra("songList")?: emptyList<Songs>()
        val initalIndex=intent.getIntExtra("position",0)
        setContent {
            MuzikCalarEkran(songList = mySongList,
                initialIndex = initalIndex,
                onBack = {finish()}
            )
        }
    }
}