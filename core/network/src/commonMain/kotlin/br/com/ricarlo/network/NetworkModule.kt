package br.com.ricarlo.network

import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { KtorHttpClient.httpClient() }
}
