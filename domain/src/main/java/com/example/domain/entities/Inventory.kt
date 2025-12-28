package com.example.domain.entities

data class Inventory (
    val assets: List<Assets>,
    val descriptions: List<InventoryItem>
)