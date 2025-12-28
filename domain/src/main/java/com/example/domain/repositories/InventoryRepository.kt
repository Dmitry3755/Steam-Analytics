package com.example.domain.repositories

import com.example.domain.entities.Inventory
import com.example.domain.utils.Result

interface InventoryRepository {
    suspend fun getInventory(steamId : String) : Result<Inventory>
}