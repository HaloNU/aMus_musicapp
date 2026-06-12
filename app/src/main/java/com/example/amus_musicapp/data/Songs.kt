package com.example.amus_musicapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Songs(
    val id:Long,
    val title: String?,
    val artist: String?,
    val data: String,
    val albumId: Long
): Parcelable
