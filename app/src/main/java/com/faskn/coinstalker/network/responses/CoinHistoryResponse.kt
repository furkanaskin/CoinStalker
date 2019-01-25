package com.faskn.coinstalker.network.responses

import com.faskn.coinstalker.model.CoinHistoryData
import com.google.gson.annotations.SerializedName

data class CoinHistoryResponse(
    @SerializedName("data")
    val `data`: CoinHistoryData,
    @SerializedName("status")
    val status: String
)