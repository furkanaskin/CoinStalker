package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("base")
    val base: String,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("order")
    val order: String,
    @SerializedName("total")
    val total: Int
)