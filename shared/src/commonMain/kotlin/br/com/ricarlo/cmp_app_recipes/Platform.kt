package br.com.ricarlo.cmp_app_recipes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
