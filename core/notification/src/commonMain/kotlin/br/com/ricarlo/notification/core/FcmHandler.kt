package br.com.ricarlo.notification.core

expect interface IFcmHandler {
    fun onNewToken(token: String)
    fun onMessageReceived(remoteMessage: Map<String, Any>)
    fun onClickMessage(remoteMessage: Map<String, Any>)
}
