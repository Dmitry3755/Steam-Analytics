package com.example.domain.entities

data class Assets(
    val appId: Int,
    val contextId: String,
    val assetId: String,
    val classId: String,
    val instanceId: String,
    val amount: String
)