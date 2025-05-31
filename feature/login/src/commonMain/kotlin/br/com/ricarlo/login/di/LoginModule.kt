package br.com.ricarlo.login.di

import br.com.ricarlo.login.data.di.dataModule
import br.com.ricarlo.login.domain.di.domainModule
import br.com.ricarlo.login.presentation.di.presentationModule

val loginModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)
