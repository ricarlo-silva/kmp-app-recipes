package br.com.ricarlo.notification

import android.app.NotificationManager
import android.content.Intent
import androidx.core.app.NotificationCompat
import br.com.ricarlo.notification.core.IFcmHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MyAndroidFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val fcmHandler by inject<IFcmHandler>()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        fcmHandler.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        fcmHandler.onMessageReceived(remoteMessage.data)

        showNotification(remoteMessage)
    }

    private fun showNotification(message: RemoteMessage) {
        val intent = Intent("com.ricarlo.notification.NOTIFICATION_CLICK").apply {
//            putExtra("data", message.data.toList().toTypedArray())
        }

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(message.notification?.title.orEmpty())
            .setContentText(message.notification?.body.orEmpty())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(createBroadcastPendingIntent(intent))

        createNotificationChannel(
            id = channelId,
            name = getString(R.string.default_notification_channel_name),
            description = getString(R.string.default_notification_channel_description)
        )

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            message.messageId.orEmpty().hashCode(),
            notificationBuilder.build()
        )
    }
}
