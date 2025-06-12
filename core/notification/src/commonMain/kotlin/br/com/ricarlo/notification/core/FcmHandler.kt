package br.com.ricarlo.notification.core

import br.com.ricarlo.common.EventBus
import br.com.ricarlo.network.utils.logger
import br.com.ricarlo.notification.data.remote.IApiNotification
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val KEY = "event"
private const val EVENT_RECEIVED = "received"
private const val EVENT_OPEN = "open"

interface IFcmHandler {
    fun onNewToken(token: String)
    fun onMessageReceived(remoteMessage: Map<String, Any>)
    fun onClickMessage(remoteMessage: Map<String, Any>)
}

internal class FcmHandler(
    private val apiNotification: IApiNotification
) : IFcmHandler {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val handler = CoroutineExceptionHandler { _, exception ->
        logger.error(exception) { "FCM error" }
    }

    override fun onNewToken(token: String) {
        logger.debug { "FCM Token: $token" }
        scope.launch(handler) {
            apiNotification.registerToken(token)
        }
    }

    override fun onMessageReceived(remoteMessage: Map<String, Any>) {
        scope.launch(handler) {
            apiNotification.registerMetric(remoteMessage.plus(KEY to EVENT_RECEIVED))
        }
    }

    override fun onClickMessage(remoteMessage: Map<String, Any>) {
        scope.launch(handler) {
            EventBus.send(event = remoteMessage)
            apiNotification.registerMetric(remoteMessage.plus(KEY to EVENT_OPEN))
        }
    }
}
