package com.example.exerciseapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.exerciseapp.R
import com.example.exerciseapp.R.raw.exercise
import kotlinx.coroutines.delay
//
//@Composable
//fun SplashScreen(onTimeout: () -> Unit) {
//    LaunchedEffect(Unit) {
//        delay(3000) // Show splash for 3 seconds
//        onTimeout() // Navigate to the next screen
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        contentAlignment = Alignment.Center
//    ) {
//        // Display the animated GIF
//        AsyncImage(
//            model = exercise,
//            contentDescription = "Animated Splash Screen",
//            modifier = Modifier.size(200.dp) // Adjust size as needed
//        )
//    }
//}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 3 seconds
        onTimeout() // Navigate to the next screen
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Display the animated GIF using Glide
        GlideImage(
            model = exercise, // Path to your GIF in the raw folder
            contentDescription = "Animated Splash Screen",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}