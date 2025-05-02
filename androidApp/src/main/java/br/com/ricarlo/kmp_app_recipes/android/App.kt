package br.com.ricarlo.kmp_app_recipes.android

import android.app.Application
import br.com.ricarlo.kmp_app_recipes.presentation.initKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
//            androidContext(this@App)
        }
    }
}
