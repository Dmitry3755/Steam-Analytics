package com.example.steamanalytics.dependencies_injection.modules

import com.example.data.api.InventoryApi
import com.example.data.api.ItemMarketApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.209:8288/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @ViewModelScoped
    fun provideInventoryApi(retrofit: Retrofit) : InventoryApi {
        return retrofit.create(InventoryApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideItemMarketApi(retrofit: Retrofit) : ItemMarketApi {
        return retrofit.create(ItemMarketApi::class.java)
    }
}