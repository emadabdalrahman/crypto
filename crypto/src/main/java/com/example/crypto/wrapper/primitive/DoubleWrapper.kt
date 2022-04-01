package com.example.crypto.wrapper.primitive

interface DoubleWrapper {
    /**
     * convert given byte array to Double
     * @param bytes that will converted
     * @return Double that represent given value
     */
    fun toDouble(bytes: ByteArray): Double

    /**
     * convert given double to byte array
     * @param double converted data
     * @return ByteArray that represent given value
     */
    fun fromDouble(double: Double): ByteArray

}