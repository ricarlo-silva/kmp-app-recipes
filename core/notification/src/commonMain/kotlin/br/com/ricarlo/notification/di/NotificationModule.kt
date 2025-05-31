package br.com.ricarlo.notification.di

import br.com.ricarlo.notification.core.FcmHandler
import br.com.ricarlo.notification.core.IFcmHandler
import br.com.ricarlo.notification.data.remote.ApiNotification
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val notificationModules = module {
    single<IFcmHandler> { FcmHandler(get()) }
    singleOf(::ApiNotification)
}
