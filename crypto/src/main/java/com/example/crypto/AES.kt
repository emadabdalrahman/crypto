package com.example.crypto

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AES(key: String) : CryptographyEngine {

    private val secretKey: SecretKey = getSecretKey(key)

    private val encryptCipher = Cipher
        .getInstance("AES")
        .apply {
            init(Cipher.ENCRYPT_MODE, secretKey)
        }

    private val decryptCipher = Cipher
        .getInstance("AES")
        .apply {
            init(Cipher.DECRYPT_MODE, secretKey)
        }

    override fun getSecretKey(password: String): SecretKey {
        val keyLength = 256
        val keyBytes = ByteArray(keyLength / 8)
        val passwordBytes: ByteArray = password.toByteArray(Charsets.UTF_8)
        val length = minOf(passwordBytes.size, keyBytes.size)
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length)
        return SecretKeySpec(keyBytes, "AES")
    }

    override fun encrypt(obj: ByteArray): ByteArray {
        return encryptCipher.doFinal(obj)
    }

    override fun decrypt(obj: ByteArray): ByteArray {
        return decryptCipher.doFinal(obj)
    }

}