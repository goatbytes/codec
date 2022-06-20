/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * A Base58 encoder/decoder.
 *
 * ### About Base58:
 *
 * > Base58 is designed with a number of usability characteristics in mind
 * > that Base64 does not consider. First, similar looking letters are
 * > omitted such as 0 (zero), O (capital o), I (capital i) and l (lower
 * > case L).  Doing so eliminates the possibility of a human being
 * > mistaking similar characters for the wrong character.  Second, the
 * > non-alphanumeric characters + (plus), = (equals), and / (slash) are
 * > omitted to make it possible to use Base58 values in all modern file
 * > systems and URL schemes without the need for further system-specific
 * > encoding schemes.  Third, by using only alphanumeric characters, easy
 * > double-click or double tap selection is possible in modern computer
 * > interfaces.  Fourth, social messaging systems do not line break on
 * > alphanumeric strings making it easier to e-mail or message Base58
 * > values when debugging systems.  Fifth, unlike Base64, there is no
 * > byte padding making many Base58 values smaller (on average) or the
 * > same size as Base64 values for values up to 64 bytes, and less than
 * > 2% larger for larger values.  Finally, Base64 has eleven encoding
 * > variations that lead to confusion among developers on which variety
 * > of Base64 to use.  This specification asserts that there is just one
 * > simple encoding mechanism for Base58, making implementations and
 * > developer interactions simpler.
 *
 * > While Base58 does have a number of beneficial usability features, it
 * > is not always a good choice for an encoding format.  For example, when
 * > encoding large amounts of data, it is 2% less efficient than
 * > base64.  Developers might avoid Base58 if a 2% increase in efficiency
 * > over large data sets is desired.
 *
 * > See: https://tools.ietf.org/id/draft-msporny-base58-02.html
 *
 * Thanks to Satoshi Nakamoto for inventing the Base58 encoding format
 * and the Bitcoin community for popularizing its usage.
 *
 * See: https://github.com/bitcoin/bitcoin/blob/master/src/base58.cpp
 *
 * @since 0.1.0
 * @author Jared Rummler
 */
object Base58 : Codec {

    override fun encode(data: ByteArray): ByteArray = Encoder.encode(data)

    override fun encode(data: String): String = Encoder.encode(data)

    override fun encodeToBytes(data: String): ByteArray = Encoder.encodeToBytes(data)

    override fun encodeToString(data: ByteArray): String = Encoder.encodeToString(data)

    override fun decode(data: ByteArray): ByteArray = Decoder.decode(data)

    override fun decode(data: String): String = Decoder.decode(data)

    override fun decodeToString(data: ByteArray): String = Decoder.decodeToString(data)

    override fun decodeToBytes(data: String): ByteArray = Decoder.decodeToBytes(data)

    object Encoder : Coder {

        override fun encode(data: ByteArray): ByteArray = base58(data)

        override fun encode(data: String): String = base58(data.encodeToByteArray()).decodeToString()

        override fun encodeToBytes(data: String): ByteArray = base58(data.encodeToByteArray())

        override fun encodeToString(data: ByteArray): String = base58(data).decodeToString()

        private fun base58(data: ByteArray): ByteArray {
            // Skip & count leading zeroes.
            var zeroes = 0
            var length = 0
            var begin = 0
            val end = data.size

            while (begin != end && data[begin] == ZERO) {
                begin++
                zeroes++
            }
            // Allocate enough space in big-endian base58 representation.
            val size = (end - begin) * 138 / 100 + 1 // log(256) / log(58), rounded up.
            val b58 = ByteArray(size)
            // Process the bytes.
            while (begin != end) {
                var carry = data[begin].toInt() and 0xFF
                // Apply "b58 = b58 * 256 + ch".
                var index = b58.size - 1
                var i = 0
                while ((carry != 0 || i < length) && (index >= 0)) {
                    carry += 256 * b58[index]
                    b58[index] = (carry % 58).toByte()
                    carry /= 58
                    i++
                    index--
                }
                length = i
                begin++
            }
            // Skip leading zeroes in base58 result.
            var index = size - length
            while (index != b58.size && b58[index] == ZERO) {
                index++
            }
            return buildString {
                repeat(zeroes) { append(ENCODED_ZERO) }
                while (index < b58.size) {
                    append(ALPHABET[b58[index].toInt()])
                    index++
                }
            }.encodeToByteArray()
        }
    }

    object Decoder : Decode {

        private val mapBase58 = intArrayOf(
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, -1, -1, -1, -1, -1,
            -1, 9, 10, 11, 12, 13, 14, 15, 16, -1, 17, 18, 19, 20, 21, -1,
            22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1,
            -1, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, -1, 44, 45, 46,
            47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
        )

        override fun decode(data: ByteArray): ByteArray = base58decode(data.decodeToString())

        override fun decode(data: String): String = base58decode(data).decodeToString()

        override fun decodeToBytes(data: String): ByteArray = base58decode(data)

        override fun decodeToString(data: ByteArray): String = base58decode(data.decodeToString()).decodeToString()

        private fun base58decode(data: String): ByteArray {
            // Skip leading spaces.
            val map = mapBase58
            var psz = 0
            while (psz < data.length && data[psz].isWhitespace()) {
                psz++
            }
            // Skip and count leading '1's.
            var zeroes = 0
            var length = 0
            while (psz < data.length && data[psz] == ENCODED_ZERO) {
                zeroes++
                psz++
            }
            // Allocate enough space in big-endian base256 representation.
            val size = (data.length - psz) * 733 / 1000 + 1 // log(58) / log(256), rounded up.
            val b256 = ByteArray(size)

            // Process the characters.
            while (psz < data.length && !data[psz].isWhitespace()) {
                // Decode base58 character
                var carry = try {
                    map[data[psz].code].also { carry ->
                        if (carry == -1) throw IndexOutOfBoundsException() // Kotlin/JS
                    }
                } catch (e: IndexOutOfBoundsException) {
                    throw IllegalArgumentException("Invalid base58 character at index: '$psz'", e)
                }
                var i = 0
                var index = b256.size - 1
                while ((carry != 0 || i < length) && (index >= 0)) {
                    carry += 58 * (b256[index].toInt() and 0xFF)
                    b256[index] = (carry % 256).toByte()
                    carry /= 256
                    index--
                    i++
                }
                length = i
                psz++
            }
            // Skip trailing spaces.
            while (psz < data.length && data[psz].isWhitespace()) psz++
            require(psz == data.length)
            // Skip leading zeroes in b256.
            var it = size - length
            val output = ByteArray(zeroes + b256.size - it)
            while (it < b256.size) {
                output[zeroes] = b256[it]
                zeroes++
                it++
            }
            return output
        }
    }

    private const val ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
    private const val ENCODED_ZERO = '1'
    private const val ZERO: Byte = 0
}
