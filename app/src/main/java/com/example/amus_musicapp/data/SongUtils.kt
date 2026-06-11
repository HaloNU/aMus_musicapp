package com.example.amus_musicapp.data

import android.content.Context
import android.provider.MediaStore

fun getSongs(context: Context): List<Songs>{
    val songs = mutableListOf<Songs>()
    val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    val selection="${MediaStore.Audio.Media.IS_MUSIC}!=0"
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    val projection=arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA,
        MediaStore.Audio.Media.ALBUM_ID

    )

    val cursor=context.contentResolver.query(
        uri,
        projection,
        selection,
        null,
        sortOrder
    )

    )

}