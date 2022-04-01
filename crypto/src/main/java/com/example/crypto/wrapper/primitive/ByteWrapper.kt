package com.example.crypto.wrapper.primitive

interface ByteWrapper {
    /**
     * convert given byte array to Byte
     * @param bytes that will converted
     * @return Byte that represent given value
     */
    fun toByte(bytes: ByteArray): Byte

    /**
     * convert given byte to byte array
     * @param byte converted data
     * @return ByteArray that represent given value
     */
    fun fromByte(byte: Byte): ByteArray
}