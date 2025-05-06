package com.accenture.pocpinning

import android.app.Application
import com.accenture.pocpinning.ui.modules.createAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        val connectionMode = ConnectionMode.PINNING

        val appModules = createAppModules(connectionMode)

        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}

enum class ConnectionMode {
    NORMAL,
    INSECURE,
    PINNING
}