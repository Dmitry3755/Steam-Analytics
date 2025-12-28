package com.example.steamanalytics.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.entities.Inventory
import com.example.domain.entities.InventoryItem
import com.example.domain.use_case.db.GetInventoryDbUseCase
import com.example.domain.use_case.GetInventoryUseCase
import com.example.domain.use_case.db.InsertInventoryDbUseCase
import com.example.domain.use_case.db.UpdateInventoryDbUseCase
import com.example.domain.utils.Result
import com.example.steamanalytics.services.SteamIdValidation
import com.example.steamanalytics.utils.ViewError
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getInventoryUseCase: GetInventoryUseCase,
    private val getInventoryDbUseCase: GetInventoryDbUseCase,
    private val insertInventoryDbUseCase: InsertInventoryDbUseCase,
    private val updateInventoryDbUseCase: UpdateInventoryDbUseCase
) :
    ViewModel() {
    var steamId: MutableState<String> = mutableStateOf("")
    lateinit var inventoryItemList: MutableList<InventoryItem>

    suspend fun getInventory(): Result<Inventory> {

        return getInventoryUseCase.invoke()
    }

    suspend fun updateInventoryDb() {
        updateInventoryDbUseCase.invoke(inventoryItemList)
    }

    suspend fun getInventoryDb(): List<InventoryItem> {
        inventoryItemList = getInventoryDbUseCase.invoke().toMutableList()
        return getInventoryDbUseCase.invoke()
    }

    suspend fun insertItemsDb() {
        insertInventoryDbUseCase.invoke(inventoryItemList)
    }
}