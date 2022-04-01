package com.example.crypto

import javax.crypto.SecretKey


interface CryptographyEngine {
    fun encrypt(obj: ByteArray): ByteArray
    fun decrypt(obj: ByteArray): ByteArray
    fun getSecretKey(key:String):SecretKey
}