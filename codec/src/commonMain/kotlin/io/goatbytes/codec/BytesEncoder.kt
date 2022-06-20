/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to encode a [ByteArray] to a new [ByteArray].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface BytesEncoder {

    /**
     * Encode the input data and return the data in a new byte array.
     *
     * @param data The input data to encode.
     * @return The encoded data in a new byte array.
     */
    fun encode(data: ByteArray): ByteArray
}