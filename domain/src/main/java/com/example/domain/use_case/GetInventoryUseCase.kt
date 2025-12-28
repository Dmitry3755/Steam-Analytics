package com.example.domain.use_case

import com.example.domain.repositories.InventoryRepository
import javax.inject.Inject
import javax.inject.Singleton

class GetInventoryUseCase @Inject constructor(private val inventoryRepository: InventoryRepository) {
    suspend operator fun invoke(steamId : String) = inventoryRepository.getInventory(steamId)
}