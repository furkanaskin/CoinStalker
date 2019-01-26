package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName

data class CoinHistoryData(
    @SerializedName("change")
    val change: Double,
    @SerializedName("history")
    val history: List<History>
)