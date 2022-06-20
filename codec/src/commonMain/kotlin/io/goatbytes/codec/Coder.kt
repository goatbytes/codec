/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * Encodes bytes.
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
interface Coder : BytesToStringEncoder,
    StringToBytesEncoder,
    StringEncoder,
    BytesEncoder