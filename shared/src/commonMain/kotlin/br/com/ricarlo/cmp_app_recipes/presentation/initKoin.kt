package br.com.ricarlo.cmp_app_recipes.presentation

import br.com.ricarlo.login.di.loginModules
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            loginModules,
        )
    }
}
