package br.com.ricarlo.notification.data.remote

import br.com.ricarlo.network.utils.toJson
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface IApiNotification {
    suspend fun registerToken(token: String)
    suspend fun registerMetric(data: Map<String, Any>)
}

internal class ApiNotification(
    private val httpClient: HttpClient
) : IApiNotification {

    override suspend fun registerToken(token: String) {
        httpClient.post("") { // TODO: URL
            contentType(ContentType.Application.Json)
            setBody(TokenRequest(token))
        }
    }

    override suspend fun registerMetric(data: Map<String, Any>) {
        httpClient.post("") { // TODO: URL
            contentType(ContentType.Application.Json)
            setBody(data.toJson())
        }
    }
}
