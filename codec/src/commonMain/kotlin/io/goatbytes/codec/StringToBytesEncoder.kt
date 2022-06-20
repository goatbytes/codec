/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to encode a [String] to a new [ByteArray].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface StringToBytesEncoder {

    /**
     * Encode the input data and return the data in a new byte array.
     *
     * @param data The input data to encode.
     * @return The encoded data in a new byte array.
     */
    fun encodeToBytes(data: String): ByteArray
}