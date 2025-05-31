package br.com.ricarlo.cmp_app_recipes.android

import android.app.Application
import br.com.ricarlo.cmp_app_recipes.presentation.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@App)
            androidLogger()
        }
    }
}
