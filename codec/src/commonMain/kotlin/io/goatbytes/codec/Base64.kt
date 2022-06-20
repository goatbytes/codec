/*
 * SPDX-FileCopyrightText: © 2022 goatbytes.io <dev@goatbytes.io>
 * SPDX-License-Identifier: MIT
 */

package io.goatbytes.codec

/**
 * This class consists exclusively of static methods for obtaining
 * encoders and decoders for the Base64 encoding scheme. The
 * implementation of this class supports the following types of Base64
 * as specified in [RFC 4648](http://www.ietf.org/rfc/rfc4648.txt) and
 * [RFC 2045](http://www.ietf.org/rfc/rfc2045.txt).
 *
 * ### Basic
 *
 * Uses "The Base64 Alphabet" as specified in Table 1 of
 * RFC 4648 and RFC 2045 for encoding and decoding operation.
 * The encoder does not add any line feed (line separator)
 * character. The decoder rejects data that contains characters
 * outside the base64 alphabet.
 *
 * ### URL and Filename safe
 *
 * Uses the "URL and Filename safe Base64 Alphabet" as specified
 * in Table 2 of RFC 4648 for encoding and decoding. The encoder
 * does not add any line feed (line separator) character.
 * The decoder rejects data that contains characters outside the
 * base64 alphabet.
 *
 * ### MIME
 *
 * Uses the "The Base64 Alphabet" as specified in Table 1 of
 * RFC 2045 for encoding and decoding operation. The encoded output
 * must be represented in lines of no more than 76 characters each
 * and uses a carriage return `'\r'` followed immediately by
 * a linefeed `'\n'` as the line separator. No line separator
 * is added to the end of the encoded output. All line separators
 * or other characters not found in the base64 alphabet table are
 * ignored in decoding operation.
 *
 * ----------------------------------------------------------------------------
 * ### [android.util.Base64] & [java.util.Base64]
 *
 * Additionally, this Base64 implementation has method signatures
 * of [android.util.Base64] which allow passing flags to encode or
 * decode base64, thus resolving issues related to unit testing
 * and the unavailability of [java.util.Base64] in various versions
 * of operating systems.
 *
 * @since  0.1.0
 * @author Jared Rummler
 */
object Base64 : Codec {

    private val ALPHABET = byteArrayOf(
        0x41, /* A */ 0x42, /* B */ 0x43, /* C */ 0x44, /* D */ 0x45, /* E */
        0x46, /* F */ 0x47, /* G */ 0x48, /* H */ 0x49, /* I */ 0x4A, /* J */
        0x4B, /* K */ 0x4C, /* L */ 0x4D, /* M */ 0x4E, /* N */ 0x4F, /* O */
        0x50, /* P */ 0x51, /* Q */ 0x52, /* R */ 0x53, /* S */ 0x54, /* T */
        0x55, /* U */ 0x56, /* V */ 0x57, /* W */ 0x58, /* X */ 0x59, /* Y */
        0x5A, /* Z */ 0x61, /* a */ 0x62, /* b */ 0x63, /* c */ 0x64, /* d */
        0x65, /* e */ 0x66, /* f */ 0x67, /* g */ 0x68, /* h */ 0x69, /* i */
        0x6A, /* j */ 0x6B, /* k */ 0x6C, /* l */ 0x6D, /* m */ 0x6E, /* n */
        0x6F, /* o */ 0x70, /* p */ 0x71, /* q */ 0x72, /* r */ 0x73, /* s */
        0x74, /* t */ 0x75, /* u */ 0x76, /* v */ 0x77, /* w */ 0x78, /* x */
        0x79, /* y */ 0x7A, /* z */ 0x30, /* 0 */ 0x31, /* 1 */ 0x32, /* 2 */
        0x33, /* 3 */ 0x34, /* 4 */ 0x35, /* 5 */ 0x36, /* 6 */ 0x37, /* 7 */
        0x38, /* 8 */ 0x39, /* 9 */ 0x2B, /* + */ 0x2F, /* / */
    )

    private val ALPHABET_URL_SAFE = byteArrayOf(
        0x41, /* A */ 0x42, /* B */ 0x43, /* C */ 0x44, /* D */ 0x45, /* E */
        0x46, /* F */ 0x47, /* G */ 0x48, /* H */ 0x49, /* I */ 0x4A, /* J */
        0x4B, /* K */ 0x4C, /* L */ 0x4D, /* M */ 0x4E, /* N */ 0x4F, /* O */
        0x50, /* P */ 0x51, /* Q */ 0x52, /* R */ 0x53, /* S */ 0x54, /* T */
        0x55, /* U */ 0x56, /* V */ 0x57, /* W */ 0x58, /* X */ 0x59, /* Y */
        0x5A, /* Z */ 0x61, /* a */ 0x62, /* b */ 0x63, /* c */ 0x64, /* d */
        0x65, /* e */ 0x66, /* f */ 0x67, /* g */ 0x68, /* h */ 0x69, /* i */
        0x6A, /* j */ 0x6B, /* k */ 0x6C, /* l */ 0x6D, /* m */ 0x6E, /* n */
        0x6F, /* o */ 0x70, /* p */ 0x71, /* q */ 0x72, /* r */ 0x73, /* s */
        0x74, /* t */ 0x75, /* u */ 0x76, /* v */ 0x77, /* w */ 0x78, /* x */
        0x79, /* y */ 0x7A, /* z */ 0x30, /* 0 */ 0x31, /* 1 */ 0x32, /* 2 */
        0x33, /* 3 */ 0x34, /* 4 */ 0x35, /* 5 */ 0x36, /* 6 */ 0x37, /* 7 */
        0x38, /* 8 */ 0x39, /* 9 */ 0x2D, /* - */ 0x5F, /* _ */
    )

    /**
     * Default values for encoder/decoder flags.
     */
    const val DEFAULT = 0

    /**
     * Encoder flag bit to omit the padding '=' characters at the end of the output (if any).
     */
    const val NO_PADDING = 1

    /**
     * Encoder flag bit to omit all line terminators (i.e., the output will be on one long line).
     */
    const val NO_WRAP = 2

    /**
     * Encoder flag bit to indicate lines should be terminated with a CRLF pair instead of just an
     * LF.  Has no effect if `NO_WRAP` is specified as well.
     */
    const val CRLF = 4

    /**
     * Encoder/decoder flag bit to indicate using the "URL and Filename Safe" variant of Base64
     * (see RFC 3548 section 4) where `-` and `_` are used in place of `+` and `/`.
     */
    const val URL_SAFE = 8

    private const val MIMELINEMAX = 76

    private val RFC4648 by lazy {
        Encoder()
    }

    private val RFC4648_URLSAFE by lazy {
        Encoder(isUrlSafe = true)
    }

    private val RFC2045 by lazy {
        Encoder(doNewLine = true, doCR = true, lineLength = MIMELINEMAX)
    }

    private val DECODER by lazy {
        Decoder()
    }

    /**
     * Returns an [Encoder] that encodes using the basic type of base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    fun getEncoder() = RFC4648

    /**
     * Returns a [Encoder] that encodes using the URL and Filename safe type base64 encoding scheme.
     *
     * @return A Base64 encoder.
     */
    fun getUrlEncoder() = RFC4648_URLSAFE

    /**
     * Returns a [Encoder] that encodes using the MIME type base64 encoding scheme.
     *
     * @return  A Base64 encoder.
     */
    fun getMimeEncoder() = RFC2045

    /**
     * Base64-encode the given data and return a newly allocated byte[] with the result.
     *
     * @param input  the data to encode
     * @param flags  controls certain features of the encoded output.
     *               Passing {@code DEFAULT} results in output that adheres to RFC 2045.
     */
    fun encode(input: String, flags: Int): String = Encoder(flags).encode(input)

    /**
     * Base64-encode the given data and return a newly allocated byte[] with the result.
     *
     * @param input  the data to encode
     * @param flags  controls certain features of the encoded output.
     *               Passing {@code DEFAULT} results in output that adheres to RFC 2045.
     */
    fun encode(input: ByteArray, flags: Int): ByteArray = Encoder(flags).encode(input)

    override fun encode(data: String): String = RFC4648.encode(data)

    override fun encode(data: ByteArray): ByteArray = RFC4648.encode(data)

    override fun encodeToString(data: ByteArray): String = RFC4648.encodeToString(data)

    override fun encodeToBytes(data: String): ByteArray = RFC4648.encodeToBytes(data)

    override fun decode(data: String): String = DECODER.decode(data)

    override fun decode(data: ByteArray): ByteArray = DECODER.decode(data)

    override fun decodeToString(data: ByteArray): String = DECODER.decodeToString(data)

    override fun decodeToBytes(data: String): ByteArray = DECODER.decodeToBytes(data)

    /**
     * A Base64 encoder.
     *
     * @param isUrlSafe True to use the URL safe base64 alphabet. Default is false.
     * @param doPadding True to add padding, false to skip padding. Default is true.
     * @param doNewLine True to add a new line. The max line length is 76. Default is false.
     * @param doCR True to use '\r' in line terminators. Default is false.
     * @param lineLength The length of a line. Default is 76 (max according to documentation).
     *
     * @since 0.1.0
     * @author Jared Rummler
     */
    class Encoder internal constructor(
        private val isUrlSafe: Boolean = false,
        private val doPadding: Boolean = true,
        private val doNewLine: Boolean = false,
        doCR: Boolean = false,
        lineLength: Int = MIMELINEMAX
    ) : Coder {

        // Rounded down to nearest multiple of 4
        private val lineLength = lineLength shr 2 shl 2

        private val lineGroups = this.lineLength / 4

        private val newLineChars = when (doNewLine) {
            true -> if (doCR) byteArrayOf(CR, LF) else byteArrayOf(LF)
            else -> byteArrayOf()
        }

        constructor(flags: Int) : this(
            flags and URL_SAFE != 0,
            flags and NO_PADDING == 0,
            flags and NO_WRAP == 0,
            flags and CRLF != 0
        )

        override fun encode(data: ByteArray): ByteArray = base64(data)

        override fun encode(data: String): String = base64(data.encodeToByteArray()).decodeToString()

        override fun encodeToString(data: ByteArray): String = base64(data).decodeToString()

        override fun encodeToBytes(data: String): ByteArray = base64(data.encodeToByteArray())

        private fun base64(data: ByteArray): ByteArray {
            val map = if (isUrlSafe) ALPHABET_URL_SAFE else ALPHABET
            val out = newBase64ByteArray(data.size)
            val end = data.size - data.size % 3
            var count = lineGroups
            var index = 0
            var i = 0

            while (i < end) {
                val b0 = data[i++].toInt()
                val b1 = data[i++].toInt()
                val b2 = data[i++].toInt()
                out[index++] = map[(b0 and 0xFF shr 2)]
                out[index++] = map[(b0 and 0x03 shl 4) or (b1 and 0xFF shr 4)]
                out[index++] = map[(b1 and 0x0F shl 2) or (b2 and 0xFF shr 6)]
                out[index++] = map[(b2 and 0x3F)]
                if (doNewLine && --count == 0) {
                    count = lineGroups
                    for (b in newLineChars) {
                        out[index++] = b
                    }
                }
            }

            when (data.size - end) {
                1 -> {
                    val b0 = data[i].toInt()
                    out[index++] = map[b0 and 0xFF shr 2]
                    out[index++] = map[b0 and 0x03 shl 4]
                    if (doPadding) {
                        out[index++] = PADDING
                        out[index] = PADDING
                    }
                }
                2 -> {
                    val b0 = data[i++].toInt()
                    val b1 = data[i].toInt()
                    out[index++] = map[(b0 and 0xFF shr 2)]
                    out[index++] = map[(b0 and 0x03 shl 4) or (b1 and 0xFF shr 4)]
                    out[index++] = map[(b1 and 0x0F shl 2)]
                    if (doPadding) {
                        out[index] = PADDING
                    }
                }
            }

            return out
        }

        private fun newBase64ByteArray(inputSize: Int): ByteArray {
            var outSize = (if (doPadding) {
                4 * ((inputSize + 2) / 3)
            } else {
                val n = inputSize % 3
                4 * (inputSize / 3) + (if (n == 0) 0 else n + 1)
            })
            if (lineLength > 0 && doNewLine) {
                outSize += (outSize - 1) / lineLength * newLineChars.size
            }
            return ByteArray(outSize)
        }

        companion object {
            private const val CR: Byte = 13 // '\r'
            private const val LF: Byte = 10 // '\n'
            private const val PADDING: Byte = 61 // '='
        }
    }

    /**
     * A Base64 decoder which handles standard, URL safe, and MIME encoded base64.
     *
     * @since 0.1.0
     * @author Jared Rummler
     */
    class Decoder() : Decode {

        override fun decode(data: ByteArray): ByteArray = base64decode(data)

        override fun decode(data: String): String = decodeToBytes(data).decodeToString()

        override fun decodeToBytes(data: String): ByteArray = decode(data.encodeToByteArray())

        override fun decodeToString(data: ByteArray): String = base64decode(data).decodeToString()

        private fun base64decode(data: ByteArray): ByteArray {
            var limit = data.size
            while (limit > 0) {
                when (data[limit - 1]) {
                    PADDING, LF, CR, SPACE, TAB -> limit--
                    else -> break
                }
            }

            val out = ByteArray((limit * 6L / 8L).toInt())
            var outCount = 0
            var inCount = 0
            var word = 0
            for (pos in 0 until limit) {
                val bits = when (val b = data[pos]) {
                    // char ASCII value
                    //  A    65    0
                    //  Z    90    25 (ASCII - 65)
                    in A..Z -> (b - 65)
                    // char ASCII value
                    //  a    97    26
                    //  z    122   51 (ASCII - 71)
                    in a..z -> (b - 71)
                    // char ASCII value
                    //  0    48    52
                    //  9    57    61 (ASCII + 4)
                    in ZERO..NINE -> (b + 4)
                    PLUS, MINUS -> 62
                    SLASH, UNDERSCORE -> 63
                    LF, CR, SPACE, TAB -> continue
                    else -> throw IllegalArgumentException(
                        "Illegal base64 character: '${Char(b.toInt())}'"
                    )
                }

                // Append this char's 6 bits to the word.
                word = word shl 6 or bits

                // For every 4 chars of input, we accumulate 24 bits of output. Emit 3 bytes.
                inCount++
                if (inCount % 4 == 0) {
                    out[outCount++] = (word shr 16).toByte()
                    out[outCount++] = (word shr 8).toByte()
                    out[outCount++] = word.toByte()
                }
            }

            when (inCount % 4) {
                1 -> {
                    // We read 1 char followed by "===". But 6 bits is a truncated byte! Fail.
                    throw IllegalArgumentException("Invalid base64")
                }
                2 -> {
                    // We read 2 chars followed by "==". Emit 1 byte with 8 of those 12 bits.
                    word = word shl 12
                    out[outCount++] = (word shr 16).toByte()
                }
                3 -> {
                    // We read 3 chars, followed by "=". Emit 2 bytes for 16 of those 18 bits.
                    word = word shl 6
                    out[outCount++] = (word shr 16).toByte()
                    out[outCount++] = (word shr 8).toByte()
                }
            }

            return when (outCount) {
                out.size -> out
                else -> out.copyOf(outCount)
            }
        }

        companion object {
            private const val TAB: Byte = 9 // '\t'
            private const val LF: Byte = 10 // '\n'
            private const val CR: Byte = 13 // '\r'
            private const val SPACE: Byte = 32 // ' '
            private const val PLUS: Byte = 43 // '+'
            private const val MINUS: Byte = 45 // '-'
            private const val SLASH: Byte = 47 // '/'
            private const val ZERO: Byte = 48 // '0'
            private const val NINE: Byte = 57 // '9'
            private const val PADDING: Byte = 61 // '='
            private const val A: Byte = 65 // 'A'
            private const val Z: Byte = 90 // 'Z'
            private const val UNDERSCORE: Byte = 95 // '_'
            private const val a: Byte = 97 // 'a'
            private const val z: Byte = 122 // 'z'
        }
    }
}
