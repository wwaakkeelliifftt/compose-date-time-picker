package com.example.date_time_dialogs__compose

import android.app.Application
import com.example.date_time_dialogs__compose.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiRootApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DiRootApp)
            modules(appModule)
        }
    }
}