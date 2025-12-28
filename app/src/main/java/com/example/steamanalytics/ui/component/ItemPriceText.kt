package com.example.steamanalytics.ui.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.entities.InventoryItem
import com.example.domain.entities.ItemMarket
import com.example.domain.utils.Result
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                val result = itemViewModel.getPriceOfItem(inventoryItem.marketHashName)
                when (result) {
                    is Result.Success -> {
                        for ((index, item) in itemViewModel.itemPriceList.withIndex()) {
                            if (item.itemId == inventoryItem.id) {
                                itemViewModel.updateCurrentPriceOfItem(index, result.data)
                                break
                            }
                        }
                        itemViewModel.insertPriceOfItem(
                            ItemMarket(
                                id = inventoryItem.id,
                                lowestPrice = result.data.lowestPrice,
                                success = result.data.success,
                                volume = result.data.volume,
                                medianPrice = result.data.medianPrice,
                                itemId = inventoryItem.id
                            )
                        )
                        price = result.data.lowestPrice ?: ""
                    }
                    is Result.Error -> price = result.exception.message
                    else -> price = ""
                }
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