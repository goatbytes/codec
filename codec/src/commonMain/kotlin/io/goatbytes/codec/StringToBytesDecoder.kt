/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to decode a [String] to a new [ByteArray].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface StringToBytesDecoder {

    /**
     * Decode the input data and return the data in a new byte array.
     *
     * @param data The input data to encode.
     * @return The decoded data in an array of bytes.
     */
    fun decodeToBytes(data: String): ByteArray
}