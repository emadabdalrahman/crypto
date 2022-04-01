package com.example.crypto.wrapper.primitive

interface LongWrapper {
    /**
     * convert given byte array to Long
     * @param bytes that will converted
     * @return Long that represent given value
     */
    fun toLong(bytes: ByteArray): Long

    /**
     * convert given long to byte array
     * @param long converted data
     * @return ByteArray that represent given value
     */
    fun fromLong(long: Long): ByteArray
}