package br.com.ricarlo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

fun Context.createPendingIntent(intent: Intent, requestCode: Int = 0): PendingIntent {
    val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    return PendingIntent.getActivity(
        this,
        requestCode,
        intent,
        flags
    )
}

fun Context.createBroadcastPendingIntent(intent: Intent, requestCode: Int = 0): PendingIntent {
    val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    return PendingIntent.getBroadcast(
        this,
        requestCode,
        intent,
        flags
    )
}

fun Context.createNotificationChannel(
    id: String,
    name: String,
    description: String? = null
) {
    val channel = NotificationChannel(
        id,
        name,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description?.let {
            this.description = it
        }
    }
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
}

fun Context.areNotificationsEnabled(channelId: String? = null): Boolean {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return when {
        !notificationManager.areNotificationsEnabled() -> {
            false
        }

        !channelId.isNullOrEmpty() -> {
            val channel = notificationManager.getNotificationChannel(channelId)
            channel.importance != NotificationManager.IMPORTANCE_NONE
        }

        else -> {
            notificationManager.notificationChannels.none { channel ->
                channel.importance == NotificationManager.IMPORTANCE_NONE
            }
        }
    }
}
