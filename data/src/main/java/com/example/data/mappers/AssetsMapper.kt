package com.example.data.mappers

import com.example.data.entities.AssetsApiResponse
import com.example.domain.entities.Assets

fun AssetsApiResponse.toAssets(): Assets {
    return Assets(
        appId = this.appId,
        contextId = this.contextId,
        assetId = this.assetId,
        classId = this.classId,
        instanceId = this.instanceId,
        amount = this.amount
    )
}