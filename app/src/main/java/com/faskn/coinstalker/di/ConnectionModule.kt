package com.faskn.coinstalker.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val connectionModule = module {
    single { createConnectionManager(androidContext(), androidApplication()) }
}

fun createConnectionManager(context: Context, application: Application): ConnectivityManager {
    return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}