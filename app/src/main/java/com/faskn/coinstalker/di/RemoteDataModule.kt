package com.faskn.coinstalker.di

import com.faskn.coinstalker.network.RetrofitService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteDataModule = module {
    single { makeRetrofitService() }
}


fun makeRetrofitService(): RetrofitService {
    val BASE_URL = "https://api.coinranking.com/v1/public/"

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create(RetrofitService::class.java)
}