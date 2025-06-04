package br.com.ricarlo.network

// TODO: Load tokens from a local storage
class TokenManager {
    fun getToken(): String {
        return BuildConfig.API_KEY
    }

    fun getRefreshToken(): String {
        return "xyz111"
    }
}
