package br.com.ricarlo.common

import androidx.navigation.NavUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val DEEPLINK_PAYLOAD_KEY = "deepLink"
private const val DEFAULT_DEEPLINK_URL = "home"

interface IDeepLinkHandler {
    fun processDeepLink(deepLink: String?)
    fun processDeepLink(remoteMessage: Map<String, Any>)
}

internal class DeepLinkHandler(
    private val scope: CoroutineScope
) : IDeepLinkHandler {

    override fun processDeepLink(deepLink: String?) {
        if (deepLink.isNullOrEmpty()) return
        scope.launch {
            EventBus.send(NavUri(deepLink))
        }
    }
    override fun processDeepLink(remoteMessage: Map<String, Any>) {
        if (remoteMessage.isEmpty()) return
        val deepLink = remoteMessage[DEEPLINK_PAYLOAD_KEY] as? String
        processDeepLink(deepLink ?: DEFAULT_DEEPLINK_URL)
    }
}
