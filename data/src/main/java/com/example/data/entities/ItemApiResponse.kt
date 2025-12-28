package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.converter.DescriptionItemApiResponseConverter
import com.example.data.converter.TagItemApiResponseConverter
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "inventory"
)
data class ItemApiResponse(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    @ColumnInfo(name = "icon_url")
    @SerializedName("icon_url")
    var iconUrl: String,
    @SerializedName("descriptions")
    @TypeConverters(DescriptionItemApiResponseConverter::class)
    var descriptions: List<DescriptionItemApiResponse>,
    @SerializedName("tradable")
    var tradable: Int,
    @SerializedName("name")
    var name: String,
    @ColumnInfo(name = "name_color")
    @SerializedName("name_color")
    var nameColor: String,
    @SerializedName("type")
    var type: String,
    @ColumnInfo(name = "market_name")
    @SerializedName("market_name")
    var marketName: String,
    @ColumnInfo(name = "market_hash_name")
    @SerializedName("market_hash_name")
    var marketHashName: String,
    @ColumnInfo(name = "market_tradable_restriction")
    @SerializedName("market_tradable_restriction")
    var marketTradableRestriction: Int,
    @SerializedName("marketable")
    var marketable: Int,
    @SerializedName("tags")
    @TypeConverters(TagItemApiResponseConverter::class)
    var tags: List<TagItemApiResponse>
)