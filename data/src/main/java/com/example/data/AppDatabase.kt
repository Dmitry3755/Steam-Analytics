package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.converter.DescriptionItemApiResponseConverter
import com.example.data.converter.TagItemApiResponseConverter
import com.example.data.dao.InventoryDao
import com.example.data.dao.ItemMarketDao
import com.example.data.entities.ItemApiResponse
import com.example.data.entities.ItemMarketApiResponse

@Database(
    entities = [ItemApiResponse::class,
        ItemMarketApiResponse::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DescriptionItemApiResponseConverter::class, TagItemApiResponseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
    abstract fun itemMarketDao(): ItemMarketDao
}