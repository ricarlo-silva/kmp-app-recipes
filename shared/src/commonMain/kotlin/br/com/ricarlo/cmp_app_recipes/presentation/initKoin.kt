package br.com.ricarlo.cmp_app_recipes.presentation

import br.com.ricarlo.login.di.loginModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            loginModule,
        )
    }
}
