package br.com.ricarlo.network

import kotlin.test.Test
import kotlin.test.assertTrue

class IosPlatformTest {

    @Test
    fun testExample() {
        assertTrue(platform().contains("iOS"), "Check iOS is mentioned")
    }
}
