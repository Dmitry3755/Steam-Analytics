package com.example.data.api

import com.example.data.entities.InventoryApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface InventoryApi {
    @GET("/inventory")
    suspend fun get(): Response<InventoryApiResponse>
}