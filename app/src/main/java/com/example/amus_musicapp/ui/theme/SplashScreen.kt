package com.example.amus_musicapp.ui.theme

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.amus_musicapp.R


@Composable
@Preview
fun SplashScreen(onStartClick:()-> Unit={}){
    Box(modifier = Modifier.fillMaxSize() ){
        Image(
            painter= painterResource(id= R.drawable.girislogo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align (Alignment.TopCenter),
            contentScale = ContentScale.Fit
        )
        Button(onClick = onStartClick,
            modifier = Modifier
                .padding(top = 87.dp, start = 20.dp , end = 20.dp)
                .align(Alignment.TopCenter)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.hotcream)
            ),shape = RoundedCornerShape(20.dp)

        ) {
            Text(
                text = stringResource(id = R.string.get_started),
                fontFamily = FontFamily.SansSerif,
                fontSize = 20.sp,
                color = colorResource(id = R.color.blue_grey),
                fontWeight = FontWeight.Bold
            )
        }

    }


}
