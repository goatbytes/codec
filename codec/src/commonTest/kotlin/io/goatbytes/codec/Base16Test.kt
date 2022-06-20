/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

import kotlin.test.Test
import kotlin.test.assertTrue

internal class Base16Test {

    @Test
    fun shouldEncodeAndDecodeBase16() {
        val testCases = arrayOf(
            "" to "",
            "fo" to "666F",
            "foo" to "666F6F",
            "foob" to "666F6F62",
            "fooba" to "666F6F6261",
            "foobar" to "666F6F626172",
            "Hello, World!" to "48656C6C6F2C20576F726C6421",
            "live long and prosper" to "6C697665206C6F6E6720616E642070726F73706572",
        )

        for ((string, hex) in testCases) {
            val encoded = Hex.encode(string)
            val decoded = Hex.decode(hex)
            assertTrue(string.equals(decoded, ignoreCase = true))
            assertTrue(hex.equals(encoded, ignoreCase = true))
        }
    }
}
