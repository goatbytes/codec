/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

import kotlin.test.Test

internal class Base58Test : CodecTest() {

    @Test
    fun shouldEncodeBase58() {
        assertEncode(
            encoder = Base58,
            expected = HELLO_WORLD_BASE58_ENCODED,
            input = HELLO_WORLD
        )
        assertEncode(
            encoder = Base58,
            expected = "USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z",
            input = "The quick brown fox jumps over the lazy dog."
        )
    }

    @Test
    fun shouldDecodeBase58() {
        assertDecode(
            decoder = Base58,
            expected = HELLO_WORLD,
            input = HELLO_WORLD_BASE58_ENCODED
        )
        assertDecode(
            decoder = Base58,
            expected = "The quick brown fox jumps over the lazy dog.",
            input = "USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z"
        )
    }

    @Test
    fun shouldThrowWhenDecodingInvalidBase58() {
        try {
            Base58.decode(INVALID_BASE58)
            throw AssertionError("Expected to throw IllegalArgumentException when decoding invalid base64")
        } catch (_: IllegalArgumentException) {
        }
    }

    companion object {
        private const val INVALID_BASE58 = "OOO"
        private const val HELLO_WORLD = "Hello, World!"
        private const val HELLO_WORLD_BASE58_ENCODED = "72k1xXWG59fYdzSNoA"
    }
}