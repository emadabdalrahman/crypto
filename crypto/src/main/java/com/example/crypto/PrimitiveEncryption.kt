package com.example.crypto

import com.example.crypto.wrapper.Wrapper

abstract class PrimitiveEncryption(
    private val wrapper: Wrapper,
    private val engine: CryptographyEngine
    ) : Encryption {

    //------------------String----------------------------
    override fun encryptString(string: String): ByteArray {
        return encrypt(wrapper.fromString(string))
    }

    override fun decryptString(byteArray: ByteArray): String {
        return wrapper.toString(decrypt(byteArray))
    }

    //------------------Char------------------------------
    override fun encryptChar(char: Char): ByteArray {
        return encrypt(wrapper.fromChar(char))
    }

    override fun decryptChar(byteArray: ByteArray): Char {
        return wrapper.toChar(decrypt(byteArray))
    }

    //------------------Boolean----------------------------
    override fun encryptBoolean(boolean: Boolean): ByteArray {
        return engine.encrypt(wrapper.fromBoolean(boolean))
    }

    override fun decryptBoolean(byteArray: ByteArray): Boolean {
        return wrapper.toBoolean(decrypt(byteArray))
    }

    //------------------Int--------------------------------
    override fun encryptInt(int: Int): ByteArray {
        return encrypt(wrapper.fromInt(int))
    }

    override fun decryptInt(byteArray: ByteArray): Int {
        return wrapper.toInt(decrypt(byteArray))
    }

    //------------------Long-----------------------------
    override fun encryptLong(long: Long): ByteArray {
        return encrypt(wrapper.fromLong(long))
    }

    override fun decryptLong(byteArray: ByteArray): Long {
        return wrapper.toLong(decrypt(byteArray))
    }

    //------------------Double----------------------------
    override fun encryptDouble(double: Double): ByteArray {
        return encrypt(wrapper.fromDouble(double))
    }

    override fun decryptDouble(byteArray: ByteArray): Double {
        return wrapper.toDouble(decrypt(byteArray))
    }

    //------------------Short----------------------------
    override fun encryptShort(short: Short): ByteArray {
        return encrypt(wrapper.fromShort(short))
    }

    override fun decryptShort(byteArray: ByteArray): Short {
        return wrapper.toShort(decrypt(byteArray))
    }

}