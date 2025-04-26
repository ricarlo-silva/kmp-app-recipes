package br.com.ricarlo.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object KtorHttpClient {
    fun httpClient(
        plugins: Array<HttpClientPlugin<Any, Any>> = emptyArray(),
    ) = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 15000
        }
        install(Auth) {
            bearer {
                loadTokens {
                    // Load tokens from a local storage and return them as the 'BearerTokens' instance
                    BearerTokens("abc123", "xyz111")
                }
                refreshTokens { // this: RefreshTokensParams
                    // Refresh tokens and return them as the 'BearerTokens' instance
                    BearerTokens("def456", "xyz111")
                }
            }
        }

        val customHeaderPlugin = createClientPlugin("CustomHeaderPlugin") {
            onRequest { request, _ ->
                request.headers.append("X-Custom-Header", "Default value")
            }
        }
        install(customHeaderPlugin)

        plugins.forEach { plugin ->
            install(plugin)
        }
    }
}

class Greeting {

    suspend fun greeting(): List<Fruit> {
        // https://github.com/public-apis/public-apis?tab=readme-ov-file
        val response = KtorHttpClient.httpClient().get("https://www.fruityvice.com/api/fruit/all")
        return response.body()
    }
}


@Serializable
data class Fruit(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Long,
    @SerialName("family") val family: String,
    @SerialName("order") val order: String,
    @SerialName("genus") val genus: String,
    @SerialName("nutritions") val nutritions: Nutritions,
)

@Serializable
data class Nutritions(
    @SerialName("calories") val calories: Long,
    @SerialName("fat") val fat: Double,
    @SerialName("sugar") val sugar: Double,
    @SerialName("carbohydrates") val carbohydrates: Double,
    @SerialName("protein") val protein: Double
)
