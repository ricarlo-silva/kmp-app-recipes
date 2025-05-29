package br.com.ricarlo.notification.di

import br.com.ricarlo.notification.core.FcmHandler
import br.com.ricarlo.notification.core.IFcmHandler
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val notificationModules = module {
    singleOf<IFcmHandler>(::FcmHandler)
}
