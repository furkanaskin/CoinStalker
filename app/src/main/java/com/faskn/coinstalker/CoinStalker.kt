package com.faskn.coinstalker

import android.app.Application
import com.faskn.coinstalker.di.appModule
import com.faskn.coinstalker.utils.TypeFaceUtil
import org.koin.android.ext.android.startKoin

class CoinStalker : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
        TypeFaceUtil().overrideFont(applicationContext, "SERIF", "ubuntu_regular.ttf")
    }

}