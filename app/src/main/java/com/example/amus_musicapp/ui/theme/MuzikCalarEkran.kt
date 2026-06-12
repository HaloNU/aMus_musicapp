package com.example.amus_musicapp.ui.theme

import android.content.ContentUris
import android.media.browse.MediaBrowser
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.AdPlaybackState
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.amus_musicapp.R
import com.example.amus_musicapp.data.Songs
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MuzikCalarEkran(
    songList: List<Songs>,
    initialIndex: Int=0,
    onBack:()->Unit
){
    val context= LocalContext.current
    val exoPlayer= remember{ExoPlayer.Builder(context).build()}

    var currentIndex by rememberSaveable{ mutableStateOf(initialIndex) }
    var isShuffle by rememberSaveable{ mutableStateOf(false) }
    var isRepeat by rememberSaveable{ mutableStateOf(false) }
    var isPlaying by rememberSaveable{ mutableStateOf(false) }
    var elapsed by rememberSaveable{ mutableStateOf(0L) }
    var duration by rememberSaveable{ mutableStateOf(0L) }
    var shuffledList by rememberSaveable{ mutableStateOf(songList) }

    val waveform=remember{getWaveform()}
    var waveformProgress by remember{ mutableStateOf(0f) }

    LaunchedEffect(currentIndex,isShuffle){
        val list=if(isShuffle) shuffledList else songList
        val song=list.getOrNull(currentIndex) ?: return@LaunchedEffect
        exoPlayer.stop()
        exoPlayer.clearMediaItems()

        val mediaUri = Uri.fromFile(java.io.File(song.data))
        exoPlayer.setMediaItem(MediaItem.fromUri(mediaUri))

        exoPlayer.prepare()
        exoPlayer.play()
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlay: Boolean) {
                isPlaying = isPlay
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                println("ExoPlayer Hatası: ${error.message}")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    duration = exoPlayer.duration
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {

            exoPlayer.release()
        }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying){
            elapsed=exoPlayer.currentPosition
            waveformProgress=if(duration>0) elapsed.toFloat()/duration.toFloat() else 0f
            delay(500)
        }
    }

    val song=(if(isShuffle) shuffledList else songList).getOrNull(currentIndex)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    Color(color = 0xff191c1f),
                    Color(color = 0xff2c2c38)
                )
            )
        )
    ){
        song?.let {
            val albumUri= ContentUris.withAppendedId(
                Uri.parse("content://media/external/audio/albumart"),it.albumId
            )
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(albumUri)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 18.dp),
                contentScale = ContentScale.Crop,
                alpha = 0.40f,
                error = painterResource(id= R.drawable.baseline_music_note_24),
                placeholder = painterResource(id = R.drawable.baseline_music_note_24)
            )
            Row (Modifier.padding(horizontal = 16.dp, vertical = 48.dp)){
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .background(Color(color = 0x30ffffff), shape = CircleShape)

                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .background(Color(color = 0x30ffffff), shape = CircleShape)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.Black)
                }
            }
            Column (modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = song?.let {
                        ContentUris.withAppendedId(
                            Uri.parse("content://media/external/audio/albumart"),
                            it.albumId
                        )
                    }
                        ?:R.drawable.baseline_music_note_24,
                    contentDescription = null,
                    modifier = Modifier
                        .size(320.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(color = 0x33ffffff), shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id= R.drawable.baseline_music_note_24),
                    placeholder = painterResource(id = R.drawable.baseline_music_note_24)

                )

                Text(
                    song?.title.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Text(
                    song?.artist.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )

            }

            Row (Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 56.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically


            ){
                //tek-döngü butonu
                IconButton(onClick = {
                    isRepeat= !isRepeat
                    exoPlayer.repeatMode=
                        if (isRepeat) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF

                }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_repeat_one_24),
                        contentDescription = null,
                        tint = if(isRepeat) Color(color = 0xFF121212) else Color.White
                    )
                }
                //bir önceki şarkıya geç butonu
                IconButton(onClick = {
                    val list=if(isShuffle) shuffledList else songList
                    currentIndex=if(currentIndex-1<0) list.size-1 else currentIndex-1

                }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_skip_previous_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                //durdur-çal butonu
                IconButton(onClick = {
                    if(exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()

                },
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, shape = CircleShape)

                ) {
                    Icon(painter = painterResource(id = if(isPlaying)R.drawable.baseline_pause_24 else R.drawable.round_play_arrow_24),
                        contentDescription = null,
                        tint = Color(color = 0xff1a1a1a)
                    )
                }

                //sonraki şarkıya geç butonu
                IconButton(onClick = {
                    val list=if(isShuffle) shuffledList else songList
                    currentIndex=(currentIndex+1)%list.size

                }) {
                    Icon(painter = painterResource(id = R.drawable.outline_skip_next_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                //karışık çal butonu
                IconButton(onClick = {
                    isShuffle=!isShuffle
                    shuffledList=if(isShuffle) songList.shuffled() else songList

                }) {
                    Icon(painter = painterResource(id = R.drawable.rounded_shuffle_24),
                        contentDescription = null,
                        tint = if(isShuffle) Color(color = 0xFF121212) else Color.White
                    )
                }

            }
        }

    }
}

fun getWaveform(): IntArray{
    val random= Random(System.currentTimeMillis())
    return IntArray(50){5+random.nextInt(50)}
}