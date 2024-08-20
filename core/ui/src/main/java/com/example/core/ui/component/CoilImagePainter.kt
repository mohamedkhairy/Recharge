package com.example.core.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun CoilImagePainter(imageUrl: String?): AsyncImagePainter {


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .error(android.R.drawable.ic_delete)
            .data(imageUrl)
            .crossfade(true)
            .crossfade(200)
            .build()
    )

    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
            )
        }
        is AsyncImagePainter.State.Error -> {
            // Show error message
            Text(
                text = "Error loading image",
                color = MaterialTheme.colorScheme.error,
            )
        }
        else ->{}
    }

    return painter
}