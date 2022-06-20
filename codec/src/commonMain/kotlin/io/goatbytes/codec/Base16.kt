/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * A base16 (a.k.a. HEX) decoder/encoder.
 *
 *     Base 16 encoding is the standard case-insensitive hex encoding and
 *     may be referred to as "base16" or "hex".
 *
 *     A 16-character subset of US-ASCII is used, enabling 4 bits to be
 *     represented per printable character.
 *
 *     The encoding process represents 8-bit groups (octets) of input bits
 *     as output strings of 2 encoded characters.  Proceeding from left to
 *     right, an 8-bit input is taken from the input data.  These 8 bits are
 *     then treated as 2 concatenated 4-bit groups, each of which is
 *     translated into a single character in the base 16 alphabet.
 *
 *     Each 4-bit group is used as an index into an array of 16 printable
 *     characters.  The character referenced by the index is placed in the
 *     output string.
 *
 *                                  The Base 16 Alphabet
 *
 *     | Value | Encoding | Value | Encoding | Value | Encoding | Value | Encoding |
 *     |-------|----------|-------|----------|-------|----------|-------|----------|
 *     | 0     | 0        | 4     | 4        | 8     | 8        | 12    | C        |
 *     | 1     | 1        | 5     | 5        | 9     | 9        | 13    | D        |
 *     | 2     | 2        | 6     | 6        | 10    | A        | 14    | E        |
 *     | 3     | 3        | 7     | 7        | 11    | B        | 15    | F        |
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
object Base16 : Codec {

    private val ALPHABET = byteArrayOf(
        0x30, /* 0 */ 0x31, /* 1 */ 0x32, /* 2 */ 0x33, /* 3 */
        0x34, /* 4 */ 0x35, /* 5 */ 0x36, /* 6 */ 0x37, /* 7 */
        0x38, /* 8 */ 0x39, /* 9 */ 0x41, /* A */ 0x42, /* B */
        0x43, /* C */ 0x44, /* D */ 0x45, /* E */ 0x46, /* F */
    )

    private const val RADIX = 16

    override fun encode(data: ByteArray): ByteArray = ByteArray(data.size * 2).apply {
        data.forEachIndexed { i, byte ->
            val unsigned = 0xFF and byte.toInt()
            this[i * 2] = ALPHABET[unsigned / RADIX]
            this[i * 2 + 1] = ALPHABET[unsigned % RADIX]
        }
    }

    override fun encode(data: String): String = encode(data.encodeToByteArray()).decodeToString()

    override fun encodeToBytes(data: String): ByteArray = encode(data.encodeToByteArray())

    override fun encodeToString(data: ByteArray): String = encode(data).decodeToString()

    override fun decode(data: ByteArray): ByteArray {
        val len = data.size
        if (len % 2 != 0) {
            throw IllegalArgumentException("Odd number of bytes in data")
        }
        val out = ByteArray(len shr 1)
        var index = 0
        var i = 0
        while (index < len) {
            val d0 = data[index++].digit()
            val d1 = data[index++].digit()
            out[i++] = ((d0 shl 4 or d1) and 0xFF).toByte()
        }
        return out
    }

    override fun decode(data: String): String = decode(data.encodeToByteArray()).decodeToString()

    override fun decodeToBytes(data: String): ByteArray = decode(data.encodeToByteArray())

    override fun decodeToString(data: ByteArray): String = decode(data).decodeToString()

    private fun Byte.digit(): Int = Char(toInt()).digitToInt(RADIX)
}
