package com.accenture.pocpinning

import android.app.Application
import com.accenture.pocpinning.ui.modules.createAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        val connectionMode = ConnectionMode.PINNING

        val pins = "jsonplaceholder.typicode.com:Mh7ufr6Yepdwv4IGnMFCJcVG9P0YeIqzaMr+euJo0/U=," +
                "jsonplaceholder.typicode.com:kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4="

        val appModules = createAppModules(connectionMode, pins)

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