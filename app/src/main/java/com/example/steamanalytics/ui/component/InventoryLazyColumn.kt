package com.example.steamanalytics.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.domain.entities.InventoryItem
import com.example.steamanalytics.R
import com.example.steamanalytics.ui.navigation.Navigation
import com.example.steamanalytics.ui.screen.navigate
import com.example.steamanalytics.ui.theme.AppTheme
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel

@Composable
fun InventoryLazyColumn(
    navController: NavController,
    viewModel: InventoryViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.horizontal_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.horizontal_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.horizontal_padding)),
        content = {
            items(
                viewModel.inventoryItemList,
                key = { item: InventoryItem -> item.id }
            ) {
                Card(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Navigation.ItemScreen.route + "/${it.id}")
                        }
                        .size(LocalConfiguration.current.screenHeightDp.dp / 5)
                        .border(
                            border = BorderStroke(
                                1.dp,
                                Color(
                                    android.graphics.Color.parseColor(
                                        it.nameColor.padStart(
                                            7,
                                            '#'
                                        )
                                    )
                                )
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        NetworkImage(it.iconUrl, this, 0.5f)
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_padding_item_column)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (it.tags[1].category == "Weapon" || it.tags[0].localizedTagName == "Gloves") {
                                Column {
                                    val hashName = it.marketHashName.filter { it.isUpperCase() }
                                    PreviewText(
                                        hashName.substring(hashName.length - 2),
                                        TextAlign.Start
                                    )
                                    PreviewText(it.name, TextAlign.Start)
                                }
                            } else if (it.tags[0].localizedTagName == "Sticker" || it.tags[0].localizedTagName == "Наклейка" || it.tags[0].localizedTagName == "Граффити" || it.tags[0].localizedTagName == "Graffiti") {
                                PreviewText(
                                    it.name.split("|").getOrElse(1) { "" },
                                    TextAlign.Center
                                )
                            } else {
                                PreviewText(it.name, TextAlign.Center)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PreviewText(text: String, textAlign: TextAlign) {
    Text(
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview
@Composable
private fun AccessoryLazyColumnPreview() {
    AppTheme {
        val navController: NavController = rememberNavController()
        InventoryLazyColumn(navController, hiltViewModel())
    }
}