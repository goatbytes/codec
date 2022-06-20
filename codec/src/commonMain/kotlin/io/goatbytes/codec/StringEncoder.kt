/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Interface definition to encode a [String] to a new [String].
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface StringEncoder {

    /**
     * Encode the input data and return the data as a new string.
     *
     * @param data The input data to encode.
     * @return The encoded data in a new string.
     */
    fun encode(data: String): String
}