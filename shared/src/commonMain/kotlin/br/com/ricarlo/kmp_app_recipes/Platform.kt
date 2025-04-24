package br.com.ricarlo.kmp_app_recipes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
