package br.com.ricarlo.kmp_app_recipes.presentation

import br.com.ricarlo.login.di.presentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            presentationModule,
        )
    }
}
