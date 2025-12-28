package com.example.steamanalytics.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.steamanalytics.R
import com.example.steamanalytics.ui.component.InformationAboutItem
import com.example.steamanalytics.ui.component.NetworkImage
import com.example.steamanalytics.ui.theme.AppTheme
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel

@Composable
fun ItemScreen(
    navController: NavController,
    id: Int,
    itemViewModel: ItemViewModel,
    inventoryViewModel: InventoryViewModel,
    appViewModel: AppViewModel
) {
    var color by remember {
        mutableStateOf<String?>(null)
    }
    var souvenir by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        for (itemTag in inventoryViewModel.inventoryItemList[id - 1].tags) {
            if (itemTag.color != null) {
                color = itemTag.color
            }
            if (itemTag.localizedTagName == "Souvenir" || itemTag.localizedTagName == "Сувенирный") {
                souvenir = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background)
            .padding(
                vertical = dimensionResource(id = R.dimen.vertical_padding),
                horizontal = dimensionResource(id = R.dimen.horizontal_padding)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(0.35f)
                .border(
                    border = BorderStroke(
                        2.dp,
                        if (souvenir) {
                            colorCard(inventoryViewModel.inventoryItemList[id - 1].nameColor)
                        } else {
                            colorCard(
                                color ?: inventoryViewModel.inventoryItemList[id - 1].nameColor
                            )
                        }
                    ),
                    shape = RoundedCornerShape(10.dp)
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                NetworkImage(inventoryViewModel.inventoryItemList[id - 1].iconUrl, this, 1f)
            }
        }
        Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.vertical_padding)))
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(0.65f)
        ) {
            InformationAboutItem(
                inventoryViewModel,
                appViewModel,
                itemViewModel, id,
                souvenir
            )
        }
    }
}

fun colorCard(color: String): Color {
    return Color(
        android.graphics.Color.parseColor(color.padStart(7, '#'))
    )
}

@Preview
@Composable
fun ItemScreenPreview() {
    AppTheme {
        val navigationController = rememberNavController()
        ItemScreen(navigationController, 0, viewModel(), viewModel(), viewModel())
    }
}