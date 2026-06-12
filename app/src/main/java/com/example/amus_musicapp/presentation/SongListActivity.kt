package com.example.amus_musicapp.presentation

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.amus_musicapp.ui.theme.AMus_musicappTheme
import com.example.amus_musicapp.ui.theme.SongListeEkran
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class SongListActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            SongListeEkran { songs, position ->
                val intent = Intent(this, MuzikCalarActivity::class.java)
                intent.putParcelableArrayListExtra("songList", ArrayList(songs))
                intent.putExtra("position", position)
                startActivity(intent)

            }






        }
    }
}