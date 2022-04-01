package com.example.crypto.wrapper

import com.example.crypto.wrapper.primitive.AbstractPrimitiveWrapper

abstract class Wrapper : AbstractPrimitiveWrapper(){
    //    fun formObject(obj: Any): ByteArray
//    fun <T> toObject(byteArray: ByteArray, c: Class<T>): T

    abstract fun serialize(obj: Any): ByteArray
    abstract fun deserialize(byteArray: ByteArray): Any?
}