package com.example.crypto.wrapper.primitive

interface FloatWrapper {
    /**
     * convert given byte array to Float
     * @param bytes that will converted
     * @return Float that represent given value
     */
    fun toFloat(bytes: ByteArray): Float

    /**
     * convert given Float to byte array
     * @param float converted data
     * @return ByteArray that represent given value
     */
    fun fromFloat(float: Float): ByteArray
}