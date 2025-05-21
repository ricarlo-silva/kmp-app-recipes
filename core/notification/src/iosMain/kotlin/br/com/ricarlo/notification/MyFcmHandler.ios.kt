package br.com.ricarlo.notification

actual class MyFcmHandler {
    actual fun onNewToken(token: String) {
        println("iOS FCM Token: $token")
    }

    actual fun onMessageReceived(remoteMessage: Map<String, String>) {
        println("iOS FCM Message Received: $remoteMessage")
    }

    actual fun onClickMessage(remoteMessage: Map<String, String>) {
        println("iOS FCM Message Clicked: $remoteMessage")
    }
}
