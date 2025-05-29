package br.com.ricarlo.notification.core

class FcmHandler: IFcmHandler {
    override fun onNewToken(token: String) {
        println("Android FCM Token: $token")
    }

    override fun onMessageReceived(remoteMessage: Map<String, Any>) {
        println("Android FCM Message Received: $remoteMessage")
    }

    override fun onClickMessage(remoteMessage: Map<String, Any>) {
        println("Android FCM Message Clicked: $remoteMessage")
    }
}

actual interface IFcmHandler {
    actual fun onNewToken(token: String)
    actual fun onMessageReceived(remoteMessage: Map<String, Any>)
    actual fun onClickMessage(remoteMessage: Map<String, Any>)
}
