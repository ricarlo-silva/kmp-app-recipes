package br.com.ricarlo.notification.di

import br.com.ricarlo.notification.core.FcmHandler
import br.com.ricarlo.notification.core.IFcmHandler
import br.com.ricarlo.notification.data.remote.ApiNotification
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationModules = module {
    singleOf(::FcmHandler) bind IFcmHandler::class
    singleOf(::ApiNotification)
}
