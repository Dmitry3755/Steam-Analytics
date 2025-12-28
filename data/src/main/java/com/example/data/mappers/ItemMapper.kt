package com.example.data.mappers

import com.example.data.entities.ItemApiResponse
import com.example.domain.entities.InventoryItem

fun ItemApiResponse.toItem(steamId : Int): InventoryItem {
    return InventoryItem(
        id = steamId,
        iconUrl = this.iconUrl,
        descriptions = this.descriptions.map { it.toItemDescription() },
        tradable = this.tradable,
        name = this.name,
        nameColor = this.nameColor,
        type = this.type,
        marketName = this.marketName,
        marketHashName = this.marketHashName,
        marketTradableRestriction = this.marketTradableRestriction,
        marketable = this.marketable,
        tags = this.tags.map { it.toItemTag() }
    )
}

fun InventoryItem.toItemApiResponse() : ItemApiResponse {
    return ItemApiResponse(
        id = this.id,
        iconUrl = this.iconUrl,
        descriptions = this.descriptions.map { it.toItemDescriptionApiResponse() },
        tradable = this.tradable,
        name = this.name,
        nameColor = this.nameColor,
        type = this.type,
        marketName = this.marketName,
        marketHashName = this.marketHashName,
        marketTradableRestriction = this.marketTradableRestriction,
        marketable = this.marketable,
        tags = this.tags.map { it.toTagItemApiResponse() }
    )
}