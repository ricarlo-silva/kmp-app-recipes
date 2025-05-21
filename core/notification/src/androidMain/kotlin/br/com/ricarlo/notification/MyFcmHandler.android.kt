package br.com.ricarlo.notification

actual class MyFcmHandler {
    actual fun onNewToken(token: String) {
        println("Android FCM Token: $token")
    }

    actual fun onMessageReceived(remoteMessage: Map<String, String>) {
        println("Android FCM Message Received: $remoteMessage")
    }

    actual fun onClickMessage(remoteMessage: Map<String, String>) {
        println("Android FCM Message Clicked: $remoteMessage")
    }
}
