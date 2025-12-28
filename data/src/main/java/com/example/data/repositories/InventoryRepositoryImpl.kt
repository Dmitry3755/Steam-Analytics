package com.example.data.repositories

import com.example.data.api.InventoryApi
import com.example.data.entities.InventoryApiResponse
import com.example.data.mappers.toInventory
import com.example.domain.entities.Inventory
import com.example.domain.repositories.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.utils.Result
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@ViewModelScoped
class InventoryRepositoryImpl @Inject constructor(private val inventoryApi: InventoryApi) :
    InventoryRepository {
    override suspend fun getInventory(steamId: String): Result<Inventory> {
        return withContext(Dispatchers.IO) {
            lateinit var response: Response<InventoryApiResponse>
            try {
                response = inventoryApi.get(steamId, "russian")
                if (response.isSuccessful) {
                    Result.Success(response.body()!!.toInventory())
                } else {
                    Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}