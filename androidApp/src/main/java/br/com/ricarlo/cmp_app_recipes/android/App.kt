package br.com.ricarlo.cmp_app_recipes.android

import android.app.Application
import br.com.ricarlo.cmp_app_recipes.presentation.initKoin

internal class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {  }
    }
}
