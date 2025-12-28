package com.example.steamanalytics.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.steamanalytics.R
import com.example.steamanalytics.utils.StickersOnItem
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel

@Composable
fun InformationAboutItem(
    inventoryViewModel: InventoryViewModel,
    appViewModel: AppViewModel,
    itemViewModel: ItemViewModel,
    id: Int,
    souvenir: Boolean
) {
    var stickersOnItem: StickersOnItem

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        PreviewText(
            text = inventoryViewModel.inventoryItemList[id - 1].name,
            color = if (souvenir)
                colorText(inventoryViewModel.inventoryItemList[id - 1].nameColor)
            else MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.horizontal_padding)))
        ItemPriceText(
            itemViewModel = itemViewModel,
            appViewModel = appViewModel,
            inventoryItem = inventoryViewModel.inventoryItemList[id - 1]
        )
        Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.horizontal_padding)))
        PreviewText(
            text = stringResource(id = R.string.item_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        for ((index, description) in inventoryViewModel.inventoryItemList[id - 1].descriptions.withIndex()) {
            if (index == inventoryViewModel.inventoryItemList[id - 1].descriptions.size - 1) {
                Column {
                    stickersOnItem = extractContent(description.value)
                    for (content in stickersOnItem.url) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = content,
                                placeholder = painterResource(R.drawable.ic_steam),
                                contentDescription = null,
                                alignment = Alignment.TopCenter,
                                contentScale = ContentScale.FillHeight,
                                filterQuality = FilterQuality.Low
                            )
                        }
                    }
                }
            } else {
                PreviewText(
                    text = description.value.replace("<i>", "").replace("</i>", ""),
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (inventoryViewModel.inventoryItemList[id - 1].tags[0].localizedTagName == "Case" || inventoryViewModel.inventoryItemList[id - 1].tags[0].localizedTagName == "Контейнер") {
                        colorText(description.color ?: "ffffff")
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    }
                )
            }
        }
    }
}

fun colorText(color: String): Color {
    return Color(
        android.graphics.Color.parseColor(color.padStart(7, '#'))
    )
}

fun extractContent(input: String): StickersOnItem {
    val regexUrl = Regex("""https?://\S+\.png""")
  //  val regexName = Regex("""Наклейка: .+""")
    return StickersOnItem(
        url = regexUrl.findAll(input).map { it.value }.toList(),
        name = listOf()
    )
}

@Composable
fun PreviewText(text: String, color: Color, style: TextStyle) {
    Text(
        text = text,
        style = style,
        textAlign = TextAlign.Start,
        color = color
    )
}