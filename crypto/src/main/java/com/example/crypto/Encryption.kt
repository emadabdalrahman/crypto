package com.example.crypto

interface Encryption {
    fun encryptString(string: String): ByteArray
    fun encryptChar(char: Char): ByteArray
    fun encryptBoolean(boolean: Boolean): ByteArray
    fun encryptInt(int: Int): ByteArray
    fun encryptLong(long: Long): ByteArray
    fun encryptDouble(double: Double): ByteArray
    fun encryptShort(short: Short): ByteArray
    fun encrypt(byteArray: ByteArray): ByteArray
    fun encryptSerializable(obj: Any):ByteArray
    fun decryptSerializable(byteArray: ByteArray):Any
    fun decrypt(byteArray: ByteArray): ByteArray
    fun decryptString(byteArray: ByteArray): String
    fun decryptChar(byteArray: ByteArray): Char
    fun decryptBoolean(byteArray: ByteArray): Boolean
    fun decryptInt(byteArray: ByteArray): Int
    fun decryptLong(byteArray: ByteArray): Long
    fun decryptDouble(byteArray: ByteArray): Double
    fun decryptShort(byteArray: ByteArray): Short
//    fun <T> decryptObject(byteArray: ByteArray, c: Class<T>): T
//    fun encryptObject(obj: Any): ByteArray

}