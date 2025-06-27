package br.com.ricarlo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.IntentCompat
import br.com.ricarlo.notification.core.IFcmHandler
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class NotificationReceiver : BroadcastReceiver(), KoinComponent {
    private val fcmHandler by inject<IFcmHandler>()

    override fun onReceive(context: Context, intent: Intent) {
        val message = IntentCompat.getParcelableExtra(
            intent, MESSAGE_KEY, RemoteMessage::class.java
        )
        val data = message?.data.orEmpty()
        fcmHandler.onClickMessage(remoteMessage = data)
    }
}
