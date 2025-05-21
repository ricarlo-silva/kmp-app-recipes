package br.com.ricarlo.notification

expect class MyFcmHandler {
    fun onNewToken(token: String)
    fun onMessageReceived(remoteMessage: Map<String, String>)
    fun onClickMessage(remoteMessage: Map<String, String>)
}
