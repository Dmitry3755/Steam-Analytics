package com.example.steamanalytics.ui.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.steamanalytics.R


@Composable
fun NetworkImage(
    url: String,
    columnScope: ColumnScope,
    weight: Float,
    stickerOnWeapon: Boolean = false
) {

    columnScope.run {
        AsyncImage(
            modifier = Modifier.weight(weight),
            model = url,
            placeholder = painterResource(R.drawable.ic_steam),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillHeight,
            filterQuality = FilterQuality.Low
        )
    }
}