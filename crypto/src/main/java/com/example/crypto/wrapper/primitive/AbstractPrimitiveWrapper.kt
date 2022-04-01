package com.example.crypto.wrapper.primitive

import com.example.crypto.wrapper.PrimitiveWrapper
import java.nio.ByteBuffer

abstract class AbstractPrimitiveWrapper : PrimitiveWrapper {


    override fun fromPrimitive(obj: Any, type: Class<*>): ByteArray {
        return when (type) {
            Byte::class.java -> fromByte(obj as Byte)
            String::class.java -> fromString(obj as String)
            Char::class.java -> fromChar(obj as Char)
            Int::class.java -> fromInt(obj as Int)
            Long::class.java -> fromLong(obj as Long)
            Short::class.java -> fromShort(obj as Short)
            Double::class.java -> fromDouble(obj as Double)
            Boolean::class.java -> fromBoolean(obj as Boolean)
            Float::class.java -> fromFloat(obj as Float)
            else -> {
                byteArrayOf()
            }
        }
    }

    override fun toPrimitive(byteArray: ByteArray, type: Class<*>): Any {
        return when (type) {
            Byte::class.java -> toByte(byteArray)
            String::class.java -> toString(byteArray)
            Char::class.java -> toChar(byteArray)
            Int::class.java -> toInt(byteArray)
            Long::class.java -> toLong(byteArray)
            Short::class.java -> toShort(byteArray)
            Double::class.java -> toDouble(byteArray)
            Float::class.java -> toFloat(byteArray)
            Boolean::class.java -> toBoolean(byteArray)
            else -> {}
        }
    }


    //---------------------Byte---------------------------

    override fun toByte(bytes: ByteArray): Byte {
        return bytes[0]
    }

    override fun fromByte(byte: Byte): ByteArray {
        return byteArrayOf(byte)
    }

    //---------------------Char---------------------------

    override fun toChar(bytes: ByteArray): Char {
        return ByteBuffer.wrap(bytes).char
    }

    override fun fromChar(char: Char): ByteArray {
        return ByteBuffer.allocate(Char.SIZE_BYTES)
            .putChar(char)
            .array()
    }

    //---------------------Boolean---------------------------

    override fun toBoolean(bytes: ByteArray): Boolean {
        return String(bytes) == "true"
    }

    override fun fromBoolean(boolean: Boolean): ByteArray {
        return boolean.toString().toByteArray()
    }

    //---------------------String---------------------------

    override fun toString(bytes: ByteArray): String {
        return String(bytes)
    }

    override fun fromString(string: String): ByteArray {
        return string.toByteArray()
    }

    //---------------------Long---------------------------

    override fun toLong(bytes: ByteArray): Long {
        return ByteBuffer.wrap(bytes).long
    }

    override fun fromLong(long: Long): ByteArray {
        return ByteBuffer.allocate(Long.SIZE_BYTES)
            .putLong(long)
            .array()
    }

    //---------------------Double---------------------------

    override fun toDouble(bytes: ByteArray): Double {
        return ByteBuffer.wrap(bytes).double
    }

    override fun fromDouble(double: Double): ByteArray {
        return ByteBuffer.allocate(Double.SIZE_BYTES)
            .putDouble(double)
            .array()
    }

    //---------------------Int---------------------------

    override fun toInt(bytes: ByteArray): Int {
        return ByteBuffer.wrap(bytes).int
    }

    override fun fromInt(int: Int): ByteArray {
        return ByteBuffer.allocate(Int.SIZE_BYTES)
            .putInt(int)
            .array()
    }

    //---------------------Short---------------------------

    override fun toShort(bytes: ByteArray): Short {
        return ByteBuffer.wrap(bytes).short
    }

    override fun fromShort(short: Short): ByteArray {
        return ByteBuffer.allocate(Short.SIZE_BYTES)
            .putShort(short)
            .array()
    }

    //---------------------Float---------------------------

    override fun toFloat(bytes: ByteArray): Float {
        return ByteBuffer.wrap(bytes).float
    }

    override fun fromFloat(float: Float): ByteArray {
        return ByteBuffer.allocate(Float.SIZE_BYTES)
            .putFloat(float)
            .array()
    }

}