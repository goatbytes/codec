/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Decodes bytes.
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface Decode : BytesToStringDecoder,
    StringToBytesDecoder,
    StringDecoder,
    BytesDecoder