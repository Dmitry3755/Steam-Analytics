package com.example.steamanalytics.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import com.example.domain.entities.InventoryItem
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ItemPriceText(
    itemViewModel: ItemViewModel,
    appViewModel: AppViewModel,
    inventoryItem: InventoryItem
) {

    var price by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(inventoryItem.marketHashName) {
        withContext(Dispatchers.IO) {
            if (appViewModel.internetState.value) {
                 price = inventoryItem.medianPrice ?: ""
            } else {
                if (itemViewModel.itemPriceList.isNotEmpty()) {
                    for (item in itemViewModel.itemPriceList) {
                        if (item.itemId == inventoryItem.id)
                            price = item.lowestPrice ?: ""
                    }
                } else {
                    price = ""
                }
            }
        }
    }

    price?.let {
        if (inventoryItem.marketable == 1) {
            Text(
                text = "Цена:" + it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }
}