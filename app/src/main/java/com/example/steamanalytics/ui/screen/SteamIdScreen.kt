package com.example.steamanalytics.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.domain.utils.Result
import com.example.steamanalytics.R
import com.example.steamanalytics.services.CheckInternetState
import com.example.steamanalytics.ui.component.AppTextField
import com.example.steamanalytics.ui.component.HeadersTextView
import com.example.steamanalytics.ui.component.MainButton
import com.example.steamanalytics.ui.navigation.Navigation
import com.example.steamanalytics.utils.ViewError
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SteamIdScreen(
    navController: NavController,
    inventoryViewModel: InventoryViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    itemViewModel: ItemViewModel = hiltViewModel(),
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    val verifyResultViewError = remember { mutableStateOf(ViewError()) }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f)
                .weight(0.2f)
                .padding(start = 30.dp, bottom = 10.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            HeadersTextView(text = stringResource(R.string.edit_text_hint_enter_steam_id))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(0.8f)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp)
                )
                .padding(
                    top = dimensionResource(id = R.dimen.text_view_padding),
                    start = dimensionResource(id = R.dimen.text_view_padding),
                    end = dimensionResource(id = R.dimen.text_view_padding)
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                AppTextField(
                    hintTextField = stringResource(id = R.string.edit_text_hint_enter_steam_id),
                    value = inventoryViewModel.steamId,
                    viewError = verifyResultViewError
                )
                Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.horizontal_padding)))
                if (verifyResultViewError.value.isError.value) {
                    Spacer(Modifier.padding(top = dimensionResource(id = R.dimen.vertical_padding)))
                    Text(
                        text = verifyResultViewError.value.errorMessage.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            MainButton(
                textButton =
                stringResource(id = R.string.button_text_continue),
                onClick = {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            appViewModel.internetState.value = CheckInternetState(context)
                            if (appViewModel.internetState.value) {
                                val result = inventoryViewModel.getInventory(verifyResultViewError)
                                if (result is Result.Success) {
                                    inventoryViewModel.inventoryItemList =
                                        result.data.descriptions.toMutableList()
                                    inventoryViewModel.insertItemsDb()
                                    itemViewModel.getPriceAllOfItemsDb()
                                    navigate(coroutineScope, navController)
                                } else {
                                    inventoryViewModel.getInventoryDb()
                                    itemViewModel.getPriceAllOfItemsDb()
                                    navigate(coroutineScope, navController)
                                }
                            } else {
                                inventoryViewModel.getInventoryDb()
                                itemViewModel.getPriceAllOfItemsDb()
                                navigate(coroutineScope, navController)
                            }
                        }
                    }
                }
            )
        }
    }
}

fun navigate(coroutineScope: CoroutineScope, navController: NavController) {
    coroutineScope.launch {
        withContext(Dispatchers.Main) {
            navController.navigate(Navigation.InventoryScreen.route)
        }
    }
}

@Preview
@Composable
fun SteamIdScreenPreview() {
    val navigationController = rememberNavController()
    val inventoryViewModel: InventoryViewModel = viewModel()
    val appViewModel: AppViewModel = viewModel()
    val itemViewModel: ItemViewModel = viewModel()
    SteamIdScreen(
        navigationController,
        inventoryViewModel,
        appViewModel,
        itemViewModel,
        LocalContext.current
    )
}