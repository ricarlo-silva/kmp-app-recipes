package br.com.ricarlo.network.utils

import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import kotlinx.serialization.json.Json

fun Throwable.logMessage() = Logger.DEFAULT.log(this.message.orEmpty())

val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
}

inline fun <reified T> T.toJson(): String? = runCatching {
    json.encodeToString(this)
}.onFailure {
   it.logMessage()
}.getOrNull()

inline fun <reified T> fromJson(json: String?): T? {
    if (json.isNullOrEmpty()) return null

    return Json.decodeFromString(json)
}
