package com.example.steamanalytics.ui.component

import androidx.compose.foundation.background
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.steamanalytics.R
import com.example.steamanalytics.ui.theme.AppTheme

@Composable
fun HeadersTextView (text: String) {
    Text (
        text = text,
        modifier = Modifier.background(color = Color.Transparent),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview
@Composable
private fun DefaultPreview() {
    AppTheme{
        HeadersTextView(stringResource(id = R.string.edit_text_hint_enter_steam_id))
    }
}