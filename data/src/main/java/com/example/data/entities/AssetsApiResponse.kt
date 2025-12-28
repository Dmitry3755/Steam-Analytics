package com.example.data.entities

import com.google.gson.annotations.SerializedName

data class AssetsApiResponse(
    @SerializedName("appid")
    val appId: Int,
    @SerializedName("contextid")
    val contextId: String,
    @SerializedName("assetid")
    val assetId: String,
    @SerializedName("classid")
    val classId: String,
    @SerializedName("instanceid")
    val instanceId: String,
    @SerializedName("amount")
    val amount: String
)