/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

import kotlin.test.assertEquals

internal abstract class CodecTest {

    protected fun assertEncode(encoder: Coder, expected: String, input: String) {
        val bytes = input.encodeToByteArray()
        val expectedBytes = expected.encodeToByteArray()
        // Test all the methods.
        assertEquals(expected, encoder.encode(input))
        assertEquals(expected, encoder.encodeToString(bytes))
        assertEqualsBytes(expectedBytes, encoder.encode(bytes))
        assertEqualsBytes(expectedBytes, encoder.encodeToBytes(input))
    }

    protected fun assertDecode(decoder: Decode, expected: String, input: String) {
        val bytes = input.encodeToByteArray()
        val expectedBytes = expected.encodeToByteArray()
        // Test all the methods
        assertEquals(expected, decoder.decode(input))
        assertEquals(expected, decoder.decodeToString(bytes))
        assertEqualsBytes(expectedBytes, decoder.decode(bytes))
        assertEqualsBytes(expectedBytes, decoder.decodeToBytes(input))
    }

    protected fun assertEqualsBytes(expected: ByteArray, actual: ByteArray, len: Int = expected.size) {
        assertEquals(len, actual.size)
        for (i in 0 until len) {
            assertEquals(expected[i], actual[i])
        }
    }
}
