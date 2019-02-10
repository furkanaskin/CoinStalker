package com.faskn.coinstalker.di

import com.faskn.coinstalker.viewmodels.CoinsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    viewModel { CoinsViewModel(get()) }
}
