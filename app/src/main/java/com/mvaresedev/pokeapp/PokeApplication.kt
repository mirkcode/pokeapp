package com.mvaresedev.pokeapp

import android.app.Application
import com.mvaresedev.pokeapp.di.appModule
import com.mvaresedev.pokeapp.di.uiModule
import com.mvaresedev.pokeapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PokeApplication)
            modules(listOf(appModule, uiModule, useCaseModule))
        }
    }
}