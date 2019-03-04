package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Coin(
    @SerializedName("allTimeHigh")
    val allTimeHigh: AllTimeHigh,
    @SerializedName("approvedSupply")
    val approvedSupply: Boolean,
    @SerializedName("change")
    val change: Double,
    @SerializedName("circulatingSupply")
    val circulatingSupply: BigDecimal,
    @SerializedName("color")
    val color: String,
    @SerializedName("confirmedSupply")
    val confirmedSupply: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("firstSeen")
    val firstSeen: Long,
    @SerializedName("history")
    val history: List<String>,
    @SerializedName("iconType")
    val iconType: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("marketCap")
    val marketCap: BigDecimal,
    @SerializedName("name")
    val name: String,
    @SerializedName("penalty")
    val penalty: Boolean,
    @SerializedName("price")
    val price: BigDecimal,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("socials")
    val socials: List<Social>,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("totalSupply")
    val totalSupply: BigDecimal,
    @SerializedName("type")
    val type: String,
    @SerializedName("volume")
    val volume: BigDecimal,
    @SerializedName("websiteUrl")
    val websiteUrl: String
)