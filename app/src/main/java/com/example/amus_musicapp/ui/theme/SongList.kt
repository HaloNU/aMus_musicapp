package com.example.amus_musicapp.ui.theme

import android.content.ContentUris
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.amus_musicapp.R
import com.example.amus_musicapp.data.Songs

@Composable
fun SongList(
    songs: List<Songs>,
    onSongClick:(Int)-> Unit,
    modifier: Modifier= Modifier
){
    LazyColumn (
        modifier = modifier.fillMaxWidth()
    ){
        itemsIndexed(items = songs) { index, song ->
            SongListItem(
                song = song,
                onClick={onSongClick(index)}
            )
        }
    }
}

@Composable
fun SongListItem(song: Songs, onClick: () -> Unit) {
    val albumArtUri = ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"),
        song.albumId
    )
    Row (modifier = Modifier.fillMaxWidth()
        .clickable { onClick() }
        .padding( all= 16.dp)
        .height(height = 72.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        AsyncImage(
            model = albumArtUri,
            contentDescription = null,
            modifier = Modifier
                .size(size = 56.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp))
                .background(Color(color = 0x33000000)),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.baseline_music_note_24),
            placeholder = painterResource(id = R.drawable.baseline_music_note_24)
        )
        Column(modifier = Modifier.padding(start = 16.dp)){
            Text(song.title.orEmpty(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
                )

        }
        androidx.compose.material3.Text(
            text = song.artist ?: "",
            color = Color(color = 0xffbbbbbb),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}