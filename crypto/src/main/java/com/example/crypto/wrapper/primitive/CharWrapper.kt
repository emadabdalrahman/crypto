package com.example.crypto.wrapper.primitive

interface CharWrapper {
    /**
     * convert given byte array to Char
     * @param bytes that will converted
     * @return Char that represent given value
     */
    fun toChar(bytes: ByteArray): Char

    /**
     * convert given char to byte array
     * @param char converted data
     * @return ByteArray that represent given value
     */
    fun fromChar(char: Char): ByteArray
}