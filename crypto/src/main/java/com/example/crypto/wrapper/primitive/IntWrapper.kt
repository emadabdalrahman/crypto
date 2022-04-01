package com.example.crypto.wrapper.primitive

interface IntWrapper {
    /**
     * convert given byte array to Int
     * @param bytes that will converted
     * @return Int that represent given value
     */
    fun toInt(bytes: ByteArray): Int

    /**
     * convert given int to byte array
     * @param int converted data
     * @return ByteArray that represent given value
     */
    fun fromInt(int: Int): ByteArray

}