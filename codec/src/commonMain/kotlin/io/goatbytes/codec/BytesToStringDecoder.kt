/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to decode a [ByteArray] to a [String].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface BytesToStringDecoder {

    /**
     * Decodes the input data and return the data in a new string.
     *
     * @param data The input data to decode.
     * @return The decoded data as a string.
     */
    fun decodeToString(data: ByteArray): String
}
