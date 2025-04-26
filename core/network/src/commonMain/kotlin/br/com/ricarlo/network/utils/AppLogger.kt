package br.com.ricarlo.network.utils

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Throwable.logError() = logger.error(this) { message }
