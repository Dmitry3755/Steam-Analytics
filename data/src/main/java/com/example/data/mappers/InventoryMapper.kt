package com.example.data.mappers

import com.example.data.entities.InventoryApiResponse
import com.example.domain.entities.Inventory

fun InventoryApiResponse.toInventory(): Inventory {
    return Inventory(
        inventory = this.inventory.mapIndexed { index, itemApiResponse -> itemApiResponse.toItem(index + 1) }
    )
}