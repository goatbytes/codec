/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

import kotlin.test.Test

internal class Base64Test : CodecTest() {

    @Test
    fun shouldEncodeBase64() {
        assertEncode(
            encoder = Base64,
            expected = HELLO_WORLD_BASE64_ENCODED,
            input = HELLO_WORLD
        )
        assertEncode(
            encoder = Base64.Encoder(Base64.NO_PADDING),
            expected = HELLO_WORLD_BASE64_ENCODED_NO_PADDING,
            input = HELLO_WORLD
        )
        assertEncode(
            encoder = Base64,
            expected = "4pyTIMOgIGxhIG1vZGU=",
            input = "✓ à la mode"
        )

        // Test bytes
        val bytes = byteArrayOf(
            0xFF.toByte(), 0xEE.toByte(), 0xDD.toByte(),
            0xCC.toByte(), 0xBB.toByte(), 0xAA.toByte(),
            0x99.toByte(), 0x88.toByte(), 0x77.toByte()
        )

        assertEqualsBytes(bytes, Base64.decodeToBytes(""), 0)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/w=="), 1)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+4="), 2)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7d"), 3)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7dzA=="), 4)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7dzLs="), 5)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7dzLuq"), 6)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7dzLuqmQ=="), 7)
        assertEqualsBytes(bytes, Base64.decodeToBytes("/+7dzLuqmYg="), 8)
    }

    @Test
    fun shouldDecodeBase64() {
        assertDecode(
            decoder = Base64,
            expected = HELLO_WORLD,
            input = HELLO_WORLD_BASE64_ENCODED
        )
        assertDecode(
            decoder = Base64,
            expected = HELLO_WORLD,
            input = HELLO_WORLD_BASE64_ENCODED_NO_PADDING
        )
        assertDecode(
            decoder = Base64,
            expected = "✓ à la mode",
            input = "4pyTIMOgIGxhIG1vZGU="
        )
    }

    @Test
    fun shouldThrowWhenDecodingInvalidBase64() {
        try {
            Base64.decode("!!!")
            throw AssertionError("Expected to throw IllegalArgumentException when decoding invalid base64")
        } catch (_: IllegalArgumentException) {
        }
    }

    companion object {
        private const val HELLO_WORLD = "Hello, World!"
        private const val HELLO_WORLD_BASE64_ENCODED = "SGVsbG8sIFdvcmxkIQ=="
        private const val HELLO_WORLD_BASE64_ENCODED_NO_PADDING = "SGVsbG8sIFdvcmxkIQ"
    }
}
