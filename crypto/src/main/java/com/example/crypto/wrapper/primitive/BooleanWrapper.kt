package com.example.crypto.wrapper.primitive

interface BooleanWrapper {
    /**
     * convert given byte array to Boolean
     * @param bytes that will converted
     * @return Boolean that represent given value
     */
    fun toBoolean(bytes: ByteArray): Boolean

    /**
     * convert given boolean to byte array
     * @param boolean converted data
     * @return ByteArray that represent given value
     */
    fun fromBoolean(boolean: Boolean): ByteArray
}