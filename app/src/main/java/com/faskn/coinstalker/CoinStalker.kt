package com.faskn.coinstalker

import android.app.Application
import com.faskn.coinstalker.di.appModule
import org.koin.android.ext.android.startKoin

class CoinStalker : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }

}