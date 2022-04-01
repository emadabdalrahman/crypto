package com.example.crypto.wrapper.primitive

interface ShortWrapper {
    /**
     * convert given byte array to Short
     * @param bytes that will converted
     * @return Short that represent given value
     */
    fun toShort(bytes: ByteArray): Short

    /**
     * convert given short to byte array
     * @param short converted data
     * @return ByteArray that represent given value
     */
    fun fromShort(short: Short): ByteArray
}