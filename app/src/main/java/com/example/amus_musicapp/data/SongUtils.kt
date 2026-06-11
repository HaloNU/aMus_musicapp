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

    cursor?.use {
        val idSutun=it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
        val titleSutun=it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
        val artistSutun=it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
        val dataSutun=it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        val albumidSutun=it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

        while(it.moveToNext()){
            val id=it.getLong(idSutun)
            val title=it.getString(titleSutun)
            val artist=it.getString(artistSutun)
            val data=it.getString(dataSutun)
            val albumId=it.getLong(albumidSutun)
            songs.add(Songs(id,title,artist,data,albumId))
        }
    }
    return songs


}