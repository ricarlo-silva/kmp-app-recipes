package br.com.ricarlo.cmp_app_recipes.android

import android.app.Application
import android.os.StrictMode
import br.com.ricarlo.cmp_app_recipes.presentation.initKoin

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectNetwork()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .penaltyLog()
                    .build()
            )
        }
        initKoin {  }
    }
}
