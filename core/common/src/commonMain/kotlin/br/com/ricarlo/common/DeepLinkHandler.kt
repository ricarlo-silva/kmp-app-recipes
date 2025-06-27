package br.com.ricarlo.common

import androidx.navigation.NavUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

interface IDeepLinkHandler {
    fun processDeepLink(deepLink: String?)
    fun processDeepLink(remoteMessage: Map<String, Any>)
}

class DeepLinkHandler : IDeepLinkHandler {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun processDeepLink(deepLink: String?) {
        if (deepLink.isNullOrEmpty()) return
        scope.launch {
            EventBus.send(NavUri(deepLink))
        }
    }
    override fun processDeepLink(remoteMessage: Map<String, Any>) {
        if (remoteMessage.isEmpty()) return
        val deepLink = remoteMessage["deepLink"] as? String
        processDeepLink(deepLink ?: "home")
    }
}
