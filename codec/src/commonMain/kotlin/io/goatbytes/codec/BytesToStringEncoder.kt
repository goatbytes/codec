/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to encode a [ByteArray] to a [String].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface BytesToStringEncoder {

    /**
     * Encode the input data and return the data in a new string.
     *
     * @param data The input data to encode.
     * @return The encoded data as a string.
     */
    fun encodeToString(data: ByteArray): String
}