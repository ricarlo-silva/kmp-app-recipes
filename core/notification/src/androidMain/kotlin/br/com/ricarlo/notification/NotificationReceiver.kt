package br.com.ricarlo.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

internal class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        MyFcmHandler().onClickMessage(
            remoteMessage = intent.extras?.keySet()?.associate {
                key -> key to intent.extras?.getString(key).orEmpty()
            } ?: emptyMap()
        )
    }
}
