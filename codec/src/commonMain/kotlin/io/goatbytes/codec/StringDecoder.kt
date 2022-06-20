/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to decode a [String] to a new [String].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface StringDecoder {

    /**
     * Decode the input data and return the data in a new string.
     *
     * @param data The input data to encode.
     * @return The decoded data as a string.
     */
    fun decode(data: String): String
}