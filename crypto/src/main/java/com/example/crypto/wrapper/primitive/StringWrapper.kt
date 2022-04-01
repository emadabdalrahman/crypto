package com.example.crypto.wrapper.primitive

interface StringWrapper {
    /**
     * convert given byte array to String
     * @param bytes that will converted
     * @return String that represent given value
     */
    fun toString(bytes: ByteArray): String

    /**
     * convert given string to byte array
     * @param string converted data
     * @return ByteArray that represent given value
     */
    fun fromString(string: String): ByteArray

}