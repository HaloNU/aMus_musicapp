package com.example.amus_musicapp.presentation

import android.Manifest
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

            }





            AMus_musicappTheme {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_AUDIO
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                val permissionState = rememberPermissionState(permission)

                LaunchedEffect(Unit) {
                    permissionState.launchPermissionRequest()
                }

                if (permissionState.status.isGranted) {
                    SongListeEkran { songs, position ->}
                } else {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Uygulamanın çalışması için müzik erişim izni vermelisiniz.")
                    }
                }
            }
        }
    }
}