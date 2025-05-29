package br.com.ricarlo.notification.data.remote

import br.com.ricarlo.network.KtorHttpClient
import br.com.ricarlo.network.utils.toJson
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiNotification {

    suspend fun registerToken(token: String) {
        KtorHttpClient
            .httpClient()
            .post("") { // TODO: URL
                contentType(ContentType.Application.Json)
                setBody(TokenRequest(token))
            }
    }

    suspend fun registerMetric(data: Map<String, Any>) {
        KtorHttpClient
            .httpClient()
            .post("") { // TODO: URL
                contentType(ContentType.Application.Json)
                setBody(data.toJson())
            }
    }
}
