package br.com.ricarlo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.ricarlo.notification.core.IFcmHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class NotificationReceiver : BroadcastReceiver(), KoinComponent {
    private val fcmHandler by inject<IFcmHandler>()

    override fun onReceive(context: Context, intent: Intent) {
        fcmHandler.onClickMessage(
            remoteMessage = intent.extras?.keySet()?.associate {
                key -> key to intent.extras?.getString(key).orEmpty()
            }.orEmpty()
        )
    }
}
