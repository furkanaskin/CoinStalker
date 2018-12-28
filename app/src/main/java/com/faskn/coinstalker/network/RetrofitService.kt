package com.faskn.coinstalker.network

import com.faskn.coinstalker.network.Response.MyResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("coins")
    fun getCoins(): Deferred<Response<MyResponse>>
}