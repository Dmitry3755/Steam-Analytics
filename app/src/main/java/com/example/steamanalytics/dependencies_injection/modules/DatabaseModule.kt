package com.example.steamanalytics.dependencies_injection.modules

import android.content.Context
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.dao.InventoryDao
import com.example.data.dao.ItemMarketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {

    @Provides
    @ViewModelScoped
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "steam_analytics.db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @ViewModelScoped
    fun provideInventoryDao(appDatabase: AppDatabase): InventoryDao {
        return appDatabase.inventoryDao()
    }

    @Provides
    @ViewModelScoped
    fun provideItemMarketDao(appDatabase: AppDatabase): ItemMarketDao {
        return appDatabase.itemMarketDao()
    }
}