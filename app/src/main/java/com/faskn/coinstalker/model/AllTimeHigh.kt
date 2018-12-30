package com.faskn.coinstalker.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class AllTimeHigh(
    @SerializedName("price")
    val price: BigDecimal,
    @SerializedName("timestamp")
    val timestamp: Number
)