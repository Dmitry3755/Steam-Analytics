package com.example.steamanalytics.ui.navigation

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.steamanalytics.ui.screen.InventoryScreen
import com.example.steamanalytics.ui.screen.ItemScreen
import com.example.steamanalytics.ui.screen.SteamIdScreen
import com.example.steamanalytics.viewmodels.AppViewModel
import com.example.steamanalytics.viewmodels.InventoryViewModel
import com.example.steamanalytics.viewmodels.ItemViewModel

@Composable
fun NavGraph(
    inventoryViewModel: InventoryViewModel,
    appViewModel: AppViewModel,
    itemViewModel: ItemViewModel,
    context: Context
) {
    val navigationController = rememberNavController()
    val backStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Navigation.SteamIdScreen
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar()
            }
        }
    ) { padding ->
        NavHost(
            navController = navigationController,
            startDestination = Navigation.SteamIdScreen.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = Navigation.SteamIdScreen.route) {
                SteamIdScreen(
                    navigationController,
                    inventoryViewModel,
                    appViewModel,
                    itemViewModel,
                    context
                )
            }
            composable(route = Navigation.InventoryScreen.route) {
                InventoryScreen(navigationController, inventoryViewModel)
            }
            composable(route = Navigation.ItemScreen.route + "/{id}") {
                val id = it.arguments?.getString("id")!!
                ItemScreen(
                    navController = navigationController,
                    id = id.toInt(),
                    itemViewModel = itemViewModel,
                    inventoryViewModel = inventoryViewModel,
                    appViewModel = appViewModel
                )
            }
        }
    }
}