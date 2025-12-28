package com.example.data.entities

data class InventoryApiResponse (
    val assets: List<AssetsApiResponse>,
    val descriptions: List<ItemApiResponse>
) {
}